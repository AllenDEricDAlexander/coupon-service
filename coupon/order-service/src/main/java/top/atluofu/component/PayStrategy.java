package top.atluofu.component;

import top.atluofu.vo.PayInfoVO;

/**
 * @ClassName: PayStrategy
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-10Day-22:18
 * @Version: 1.0
 */
public interface PayStrategy {


    /**
     * 下单
     * @return
     */
    String unifiedorder(PayInfoVO payInfoVO);


    /**
     *  退款
     * @param payInfoVO
     * @return
     */
    default String refund(PayInfoVO payInfoVO){return "";}


    /**
     * 查询支付是否成功
     * @param payInfoVO
     * @return
     */
    default String queryPaySuccess(PayInfoVO payInfoVO){return "";}


}