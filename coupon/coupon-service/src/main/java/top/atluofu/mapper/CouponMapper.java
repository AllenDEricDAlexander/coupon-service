package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.atluofu.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
@Mapper
public interface CouponMapper extends BaseMapper<CouponDO> {

    int reduceStock(long couponId, Integer stock);
}
