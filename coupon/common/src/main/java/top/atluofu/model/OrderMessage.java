package top.atluofu.model;

import lombok.Data;

/**
 * @ClassName: OrderMessage
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-09Day-21:48
 * @Version: 1.0
 */
@Data
public class OrderMessage {

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 订单号
     */
    private String outTradeNo;

}