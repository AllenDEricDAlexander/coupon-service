package top.atluofu.component;

import top.atluofu.vo.PayInfoVO;

/**
 * @ClassName: PayStrategyContext
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-10Day-22:18
 * @Version: 1.0
 */

public class PayStrategyContext {

    private PayStrategy payStrategy;

    public PayStrategyContext(PayStrategy payStrategy){

        this.payStrategy = payStrategy;
    }


    /**
     * 根据支付策略，调用不同的支付
     * @param payInfoVO
     * @return
     */
    public String executeUnifiedorder(PayInfoVO payInfoVO){
        return this.payStrategy.unifiedorder(payInfoVO);
    }


    /**
     * 根据支付的策略，调用不同的查询订单支持状态
     * @param payInfoVO
     * @return
     */
    public String executeQueryPaySuccess(PayInfoVO payInfoVO){
        return this.payStrategy.queryPaySuccess(payInfoVO);

    }


}
