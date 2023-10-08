package top.atluofu.model;

import lombok.Data;

/**
 * @ClassName: ProductMessage
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-08Day-18:44
 * @Version: 1.0
 */
@Data
public class ProductMessage {


    /**
     * 消息队列id
     */
    private long messageId;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 库存锁定taskId
     */
    private long taskId;
}