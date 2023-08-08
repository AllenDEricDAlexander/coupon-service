package top.atluofu.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UserLoginRequest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-13:44
 * @Version: 1.0
 */
@Data
@ApiModel(value = "user login obj",description = "user login obj")
public class UserLoginRequest {

    @ApiModelProperty(value = "pwd pwd",example = "12345678")
    private String pwd;

    @ApiModelProperty(value = "mail",example = "mqa010225@163.com")
    private String mail;
}
