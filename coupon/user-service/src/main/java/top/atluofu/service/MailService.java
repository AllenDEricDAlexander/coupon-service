package top.atluofu.service;

import top.atluofu.enums.SendCodeEnum;
import top.atluofu.utils.JsonData;

public interface MailService {
    void sendMail(String to,String subject, String content);

    JsonData sendCode(SendCodeEnum sendCodeType, String to);

    boolean checkCode(SendCodeEnum sendCodeEnum, String to,String code);
}
