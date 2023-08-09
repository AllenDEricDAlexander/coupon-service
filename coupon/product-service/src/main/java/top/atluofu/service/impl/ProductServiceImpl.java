package top.atluofu.service.impl;

import top.atluofu.model.ProductDO;
import top.atluofu.mapper.ProductMapper;
import top.atluofu.service.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

}
