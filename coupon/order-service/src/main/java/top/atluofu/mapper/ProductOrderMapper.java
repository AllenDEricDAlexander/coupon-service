package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.atluofu.model.ProductOrderDO;
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
public interface ProductOrderMapper extends BaseMapper<ProductOrderDO> {

}
