package top.atluofu.service.impl;

import top.atluofu.model.ProductOrderDO;
import top.atluofu.mapper.ProductOrderMapper;
import top.atluofu.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

}
