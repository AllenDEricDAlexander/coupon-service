package top.atluofu.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.atluofu.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}
