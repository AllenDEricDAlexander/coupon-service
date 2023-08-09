package top.atluofu.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: CartItemReq
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-21:06
 * @Version: 1.0
 */

@ApiModel
@Data
public class CartItemReq {
    @ApiModelProperty(value = "product_id", example = "11")
    @JsonProperty("product_id")
    private Long productId;

    @ApiModelProperty(value = "buy_num", example = "11")
    @JsonProperty("buy_num")
    private int buyNum;

}
