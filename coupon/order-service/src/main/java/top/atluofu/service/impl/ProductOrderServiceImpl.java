package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import top.atluofu.model.ProductOrderDO;
import top.atluofu.mapper.ProductOrderMapper;
import top.atluofu.request.ConfirmOrderRequest;
import top.atluofu.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.utils.JsonData;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Service
@Slf4j
@Primary
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {
    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Override
    public JsonData confirmOrder(ConfirmOrderRequest orderRequest) {
        return null;
    }

    @Override
    public String queryProductOrderState(String outTradeNo) {
        ProductOrderDO productOrderDO = productOrderMapper.selectOne(new QueryWrapper<ProductOrderDO>().eq("out_trade_no",outTradeNo));

        if(productOrderDO == null){
            return "";
        }else {
            return productOrderDO.getState();
        }
    }
}
