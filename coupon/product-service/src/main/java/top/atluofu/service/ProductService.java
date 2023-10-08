package top.atluofu.service;

import top.atluofu.model.ProductDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.model.ProductMessage;
import top.atluofu.req.LockProductRequest;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.ProductVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
public interface ProductService extends IService<ProductDO> {

    Map<String, Object> pageCouponActivity(int page, int size);

    ProductVO findDetailById(long productId);

    List<ProductVO> findProductByIdBatch(List<Long> productIdList);

    JsonData lockProductStock(LockProductRequest lockProductRequest);

    boolean releaseProductStock(ProductMessage productMessage);
}
