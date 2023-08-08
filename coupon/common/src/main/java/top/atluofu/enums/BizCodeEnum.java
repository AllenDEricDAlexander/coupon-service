package top.atluofu.enums;

/**
 * @ClassName: BizCodeEnum
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-06Day-20:56
 * @Version: 1.0
 */
public enum BizCodeEnum {
    /**
     * 通用操作码
     */
    OPS_REPEAT(110001,"重复操作"),

    /**
     *验证码
     */
    CODE_TO_ERROR(240001,"接收号码不合规"),
    CODE_LIMITED(240002,"验证码发送过快"),
    CODE_ERROR(240003,"验证码错误"),
    CODE_CAPTCHA(240101,"图形验证码错误"),
    FILE_UPLOAD_USER_IMG_FAIL(240102,"image upload failed"),

    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001,"账号已经存在"),
    ACCOUNT_UNREGISTER(250002,"账号不存在"),
    ACCOUNT_PWD_ERROR(250003,"账号或者密码错误"), SUCCESS(100000, "success"),ACCOUNT_UNLOGN(2500004,"账号未登录");

    private  int code;
    private String msg;

    private BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

}
