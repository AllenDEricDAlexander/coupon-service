package top.atluofu.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.enums.SendCodeEnum;
import top.atluofu.service.MailService;
import top.atluofu.utils.CommonUtil;
import top.atluofu.utils.JsonData;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: NotifyController
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-14:56
 * @Version: 1.0
 */
@Api(tags = "Captaha Notice moedl")
@Slf4j
@RestController
@RequestMapping("/api/user/v1")
public class NotifyController {

    private final Producer captchaProducer;
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    private final StringRedisTemplate redisTemplate;

    private final MailService mailService;

    public NotifyController(Producer captchaProducer, StringRedisTemplate redisTemplate, MailService mailService) {
        this.captchaProducer = captchaProducer;
        this.redisTemplate = redisTemplate;
        this.mailService = mailService;
    }

    @GetMapping("captcha")
    @ApiOperation("get captcha code")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String cacheKey = getCaptchaKey(request);
        if(redisTemplate.opsForValue().get(cacheKey) != null){
            return;
        }
        String text = captchaProducer.createText();
        log.info("验证码：" + text);

        redisTemplate.opsForValue().set(cacheKey,text,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);
        BufferedImage image = captchaProducer.createImage(text);
        ServletOutputStream bufferedImage = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "create_date-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            bufferedImage = response.getOutputStream();
            ImageIO.write(image, "jpg", bufferedImage);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                assert bufferedImage != null;
                bufferedImage.flush();
            } catch (IOException e) {
               e.printStackTrace();
            }
            try {
                bufferedImage.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
        }
    }

    private   String getCaptchaKey(HttpServletRequest request){
        String ip = CommonUtil.getIpAddr(request);
        String header = request.getHeader("User-Agent");
        return "user-service:captcha:" + CommonUtil.MD5(ip + header);
    }


    /**
     * 支持手机号、邮箱发送验证码
     * @return
     */
    @ApiOperation("发送验证码")
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@ApiParam("收信人") @RequestParam(value = "to", required = true)String to,
                                     @ApiParam("图形验证码") @RequestParam(value = "captcha", required = true)String  captcha,
                                     HttpServletRequest request){

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key).toString();

        if(captcha!=null && cacheCaptcha!=null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);
            return mailService.sendCode(SendCodeEnum.USER_REGISTER,to);
        }else {
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA);
        }
    }

}
