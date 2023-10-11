package top.atluofu.service;

import top.atluofu.enums.ProductOrderPayTypeEnum;
import top.atluofu.model.OrderMessage;
import top.atluofu.model.ProductOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.request.ConfirmOrderRequest;
import top.atluofu.request.RepayOrderRequest;
import top.atluofu.utils.JsonData;

import java.util.Map;

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

    JsonData handlerOrderCallbackMsg(ProductOrderPayTypeEnum productOrderPayTypeEnum, Map<String, String> paramsMap);

    Map<String, Object> page(int page, int size, String state);

    JsonData repay(RepayOrderRequest repayOrderRequest);
}
