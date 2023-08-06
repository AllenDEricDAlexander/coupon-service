package top.atluofu.service.impl;

import top.atluofu.model.UserDO;
import top.atluofu.mapper.UserMapper;
import top.atluofu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
