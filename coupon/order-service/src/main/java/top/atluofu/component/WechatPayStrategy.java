package top.atluofu.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.atluofu.vo.PayInfoVO;

/**
 * @ClassName: WechatPayStrategy
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-10Month-10Day-22:19
 * @Version: 1.0
 */

@Slf4j
@Service
public class WechatPayStrategy implements PayStrategy {

    @Override
    public String unifiedorder(PayInfoVO payInfoVO) {


        return null;
    }

    @Override
    public String refund(PayInfoVO payInfoVO) {
        return null;
    }

    @Override
    public String queryPaySuccess(PayInfoVO payInfoVO) {
        return null;
    }
}
