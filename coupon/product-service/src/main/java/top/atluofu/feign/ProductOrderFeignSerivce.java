package top.atluofu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.atluofu.utils.JsonData;

/**
 * @ClassName: ProductOrderFeignSerivce
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-08Day-18:52
 * @Version: 1.0
 */
@FeignClient(name = "xdclass-order-service")
public interface ProductOrderFeignSerivce {


    /**
     * 查询订单状态
     *
     * @param outTradeNo
     * @return
     */
    @GetMapping("/api/order/v1/query_state")
    JsonData queryProductOrderState(@RequestParam("out_trade_no") String outTradeNo);


}