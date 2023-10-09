package top.atluofu.service;

import top.atluofu.model.OrderMessage;
import top.atluofu.model.ProductOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.request.ConfirmOrderRequest;
import top.atluofu.utils.JsonData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    JsonData confirmOrder(ConfirmOrderRequest orderRequest);

    String queryProductOrderState(String outTradeNo);

    boolean closeProductOrder(OrderMessage orderMessage);
}
