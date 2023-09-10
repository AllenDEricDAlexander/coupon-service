package top.atluofu.service;


import top.atluofu.enums.SendCodeEnum;
import top.atluofu.utils.JsonData;

public interface NotifyService {

    /**
     * 发送验证码
     * @param sendCodeEnum
     * @param to
     * @return
     */
    JsonData sendCode(SendCodeEnum sendCodeEnum, String to );


    /**
     * 判断验证码是否一样
     * @param sendCodeEnum
     * @param to
     * @param code
     * @return
     */
    boolean checkCode(SendCodeEnum sendCodeEnum,String to, String code);




}
