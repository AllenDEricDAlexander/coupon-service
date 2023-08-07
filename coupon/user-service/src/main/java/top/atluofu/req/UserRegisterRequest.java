package top.atluofu.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UserRegisterRequest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-20:49
 * @Version: 1.0
 */
@Data
@ApiModel(value = "user reg obj",description = "user reg obj")
public class UserRegisterRequest {
    @ApiModelProperty(value = "nick name",example = "allen")
    private String name;

    @ApiModelProperty(value = "pwd pwd",example = "12345678")
    private String pwd;

    @ApiModelProperty(value = "headImg",example = "https://at-luo-fu-store.oss-cn-hangzhou.aliyuncs.com/test/2023/08/07/76348285-9b9b-4836-9fa3-2ae25f8af30e.png")
    @JsonProperty("head_img")
    private String headImg;

    @ApiModelProperty(value = "slogan",example = "none")
    private String slogan;

    @ApiModelProperty(value = "sex",example = "1")
    private Integer sex;

    @ApiModelProperty(value = "mail",example = "mqa010225@163.com")
    private String mail;

    @ApiModelProperty(value = "code",example = "232323")
    private String code;


}
