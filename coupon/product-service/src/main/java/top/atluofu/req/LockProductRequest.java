package top.atluofu.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: LockProductRequest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-08Day-18:41
 * @Version: 1.0
 */
@ApiModel(value = "商品锁定对象",description = "商品锁定对象协议")
@Data
public class LockProductRequest {

    @ApiModelProperty(value = "订单id",example = "12312312312")
    @JsonProperty("order_out_trade_no")
    private String orderOutTradeNo;

    @ApiModelProperty(value = "订单项")
    @JsonProperty("order_item_list")
    private List<OrderItemRequest> orderItemList;
}