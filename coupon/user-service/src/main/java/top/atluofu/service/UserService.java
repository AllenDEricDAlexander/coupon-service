package top.atluofu.service;

import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import top.atluofu.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.req.UserRegisterRequest;
import top.atluofu.utils.JsonData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
public interface UserService extends IService<UserDO> {
    JsonData register(UserRegisterRequest userRegisterRequest);
}
