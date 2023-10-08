package top.atluofu.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: OrderItemRequest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-08Day-18:41
 * @Version: 1.0
 */
@ApiModel(value = "商品子项")
@Data
public class OrderItemRequest {


    @ApiModelProperty(value = "商品id",example = "1")
    @JsonProperty("product_id")
    private long productId;

    @ApiModelProperty(value = "购买数量",example = "2")
    @JsonProperty("buy_num")
    private int buyNum;
}