package top.atluofu.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: PayUrlConfig
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-10Day-21:09
 * @Version: 1.0
 */

@Configuration
@Data
public class PayUrlConfig {

    /**
     * 支付成功页面跳转
     */
    @Value("${alipay.success_return_url}")
    private String alipaySuccessReturnUrl;


    /**
     * 支付成功，回调通知
     */
    @Value("${alipay.callback_url}")
    private String alipayCallbackUrl;
}