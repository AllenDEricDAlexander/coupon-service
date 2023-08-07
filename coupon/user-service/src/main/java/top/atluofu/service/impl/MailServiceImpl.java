package top.atluofu.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.atluofu.constant.CacheKey;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.enums.SendCodeEnum;
import top.atluofu.service.MailService;
import top.atluofu.utils.CheckUtil;
import top.atluofu.utils.CommonUtil;
import top.atluofu.utils.JsonData;

import java.util.concurrent.TimeUnit;


/**
 * @ClassName: MailServiceImpl
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-17:08
 * @Version: 1.0
 */

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private static final String SUBJECT = "code";

    private static final String CODEFORMAT = "code is %s";

    private static final long CODE_EXPIRED = 60 * 1000 * 10L;

    private final JavaMailSender mailSender;

    private final StringRedisTemplate redisTemplate;


    @Value("${spring.mail.from}")
    private String from;

    public MailServiceImpl(JavaMailSender mailSender, StringRedisTemplate redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void sendMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
        log.info("邮件发成功:{}", message.toString());
    }

    @Override
    public JsonData sendCode(SendCodeEnum sendCodeType, String to) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeType.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            if (CommonUtil.getCurrentTimestamp() - ttl < 1000 * 60 * 10) {
                log.info("重复发送验证码,时间间隔:{} 秒", (CommonUtil.getCurrentTimestamp() - ttl) / 1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }

        String randomCode = CommonUtil.getRandomCode(6);
        String value = randomCode + "_" + CommonUtil.getCurrentTimestamp();
        redisTemplate.opsForValue().set(cacheKey, value, CODE_EXPIRED, TimeUnit.SECONDS);

        if (CheckUtil.isEmail(to)) {
            //邮箱验证码
            this.sendMail(to, SUBJECT, String.format(CODEFORMAT, randomCode));
            return JsonData.buildSuccess();
        } else if (CheckUtil.isPhone(to)) {
            //短信验证码
        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeType, String to, String code) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeType.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey).toString();
        if (!StringUtils.isBlank(cacheValue)) {
            String cacheCode = cacheValue.split("_")[0];
            System.out.println(cacheCode);
            if (code.equals(cacheCode)) {
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;

    }
}
