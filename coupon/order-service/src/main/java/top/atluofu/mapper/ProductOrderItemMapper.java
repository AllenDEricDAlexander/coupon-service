package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.atluofu.model.ProductOrderItemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Mapper
public interface ProductOrderItemMapper extends BaseMapper<ProductOrderItemDO> {
    /**
     * 批量插入
     * @param list
     */
    void insertBatch( @Param("orderItemList") List<ProductOrderItemDO> list);
}
