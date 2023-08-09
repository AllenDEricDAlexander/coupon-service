package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.atluofu.enums.CouponPublishEnum;
import top.atluofu.model.ProductDO;
import top.atluofu.mapper.ProductMapper;
import top.atluofu.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.vo.ProductVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {
        Page<ProductDO> couponPageInfo = new Page<>(page, size);
        Page<ProductDO> couponPage = productMapper.selectPage(couponPageInfo, null);
        HashMap<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", couponPage.getTotal());
        pageMap.put("total_page", couponPage.getPages());
        pageMap.put("current_data", couponPage.getRecords().stream().map(this::beanProcess).collect(Collectors.toList()));
        return pageMap;
    }

    @Override
    public ProductVO findDetailById(long productId) {
        return beanProcess(productMapper.selectById(productId));
    }

    @Override
    public List<ProductVO> findProductByIdBatch(List<Long> productIdList) {
        List<ProductDO> id = productMapper.selectList(new QueryWrapper<ProductDO>().in("id", productIdList));
        return id.stream().map(this::beanProcess).collect(Collectors.toList());
    }

    private ProductVO beanProcess(ProductDO obj) {
        ProductVO couponVO = new ProductVO();
        BeanUtils.copyProperties(obj, couponVO);
        return couponVO;
    }
}
