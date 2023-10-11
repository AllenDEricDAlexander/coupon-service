package top.atluofu.constant;

/**
 * @ClassName: TimeConstant
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-11Day-21:16
 * @Version: 1.0
 */
public class TimeConstant {

    /**
     *
     * 支付订单的有效时长，超过未支付则关闭订单
     *
     * 订单超时，毫秒，默认30分钟
     */
    public static final long ORDER_PAY_TIMEOUT_MILLS = 5*60*1000;

}
