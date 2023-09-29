package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.atluofu.model.CouponTaskDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author atluofu
 * @since 2023-09-10
 */
@Mapper
public interface CouponTaskMapper extends BaseMapper<CouponTaskDO> {

    int insertBatch(List<CouponTaskDO> couponTaskDOList);
}
