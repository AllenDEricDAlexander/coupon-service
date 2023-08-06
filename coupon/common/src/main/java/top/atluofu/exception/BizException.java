package top.atluofu.exception;

import lombok.Data;
import top.atluofu.enums.BizCodeEnum;

/**
 * @ClassName: BizException
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-06Day-20:55
 * @Version: 1.0
 */
@Data
public class BizException extends RuntimeException{

    private Integer code;
    private String msg;

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BizException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }
}
