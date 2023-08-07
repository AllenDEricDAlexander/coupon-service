package top.atluofu.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.atluofu.utils.CommonUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    public NotifyController(Producer captchaProducer, StringRedisTemplate redisTemplate) {
        this.captchaProducer = captchaProducer;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("captcha")
    @ApiOperation("get captcha code")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String text = captchaProducer.createText();
        log.info("验证码：" + text);
        String cacheKey = getCaptchaKey(request);
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

    private String getCaptchaKey(HttpServletRequest request){
        String ip = CommonUtil.getIpAddr(request);
        String header = request.getHeader("User-Agent");
        return "user-service:captcha:" + CommonUtil.MD5(ip + header);
    }

}
