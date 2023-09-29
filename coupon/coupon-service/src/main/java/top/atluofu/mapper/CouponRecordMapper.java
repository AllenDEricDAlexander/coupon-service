package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.atluofu.model.CouponRecordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
@Mapper
public interface CouponRecordMapper extends BaseMapper<CouponRecordDO> {

    int lockUseStateBatch(Long id, String name, List<Long> lockCouponRecordIds);

    void updateState(Long couponRecordId, String name);
}
