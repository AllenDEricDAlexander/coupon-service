package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.atluofu.model.ProductDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {

    /**
     * 锁定商品库存
     * @param productId
     * @param buyNum
     * @return
     */
    int lockProductStock(@Param("productId") long productId, @Param("buyNum") int buyNum);

    /**
     * 解锁商品存储
     * @param productId
     * @param buyNum
     */
    void unlockProductStock(@Param("productId")Long productId, @Param("buyNum")Integer buyNum);
}
