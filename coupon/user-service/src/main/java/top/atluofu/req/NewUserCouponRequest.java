package top.atluofu.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: NewUserCouponRequest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-09Month-02Day-14:17
 * @Version: 1.0
 */
@Data
@ApiModel
public class NewUserCouponRequest {
    @ApiModelProperty(value = "user id",example = "19")
    @JsonProperty("user_id")
    private Long userId;

    @ApiModelProperty(value = "user name",example = "allen")
    private String name;
}
