package top.atluofu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName: RepayOrderRequest
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-11Day-21:26
 * @Version: 1.0
 */

@Data
public class RepayOrderRequest {


    /**
     * 订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;



    /**
     * 支付类型- 微信-银行卡-支付宝
     */
    @JsonProperty("pay_type")
    private String payType;



    /**
     * 订单号
     */
    @JsonProperty("client_type")
    private String clientType;
}
