package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.enums.SendCodeEnum;
import top.atluofu.interceptor.LoginInterceptor;
import top.atluofu.model.LoginUser;
import top.atluofu.model.UserDO;
import top.atluofu.mapper.UserMapper;
import top.atluofu.req.UserLoginRequest;
import top.atluofu.req.UserRegisterRequest;
import top.atluofu.service.MailService;
import top.atluofu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.utils.CommonUtil;
import top.atluofu.utils.JWTUtils;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.UserVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final MailService mailService;

    private final UserMapper userMapper;

    private final StringRedisTemplate redisTemplate;

    public UserServiceImpl(MailService mailService, UserMapper userMapper, StringRedisTemplate redisTemplate) {
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @GlobalTransactional
    public JsonData register(UserRegisterRequest userRegisterRequest) {
        boolean checkCode = false;

        if (StringUtils.isNotBlank(userRegisterRequest.getMail())) {
            checkCode = mailService.checkCode(SendCodeEnum.USER_REGISTER, userRegisterRequest.getMail(), userRegisterRequest.getCode());
            System.out.println(checkCode + "is do check email is blank and check code");
        }
        if (!checkCode) {
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userRegisterRequest, userDO);
        userDO.setCreateTime(new Date());
        userDO.setSlogan("none");
        //生成秘钥
        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
        //密码 + 加盐处理
        String cryptPwd = Md5Crypt.md5Crypt(userRegisterRequest.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);
        if (checkUnique(userDO.getMail())) {
            int rows = userMapper.insert(userDO);
            log.info("rows:{},register :{}", rows, userDO.toString());
            userRegisterInitTask(userDO);
            return JsonData.buildResult(BizCodeEnum.SUCCESS);
        } else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }

    @Override
    public JsonData login(UserLoginRequest loginRequest) {
        List<UserDO> list = userMapper.selectList(
                new QueryWrapper<UserDO>().eq("mail", loginRequest.getMail()));
        if (list != null && list.size() == 1) {
            UserDO userDO = list.get(0);
            String cryptPwd = Md5Crypt.md5Crypt(loginRequest.getPwd().getBytes(), userDO.getSecret());
            if (cryptPwd.equals(userDO.getPwd())) {
                LoginUser userDTO = LoginUser.builder().build();
                BeanUtils.copyProperties(userDO, userDTO);
                String token = JWTUtils.geneJsonWebToken(userDTO);
                // todo access token refresh
//                String s = CommonUtil.generateUUID();
//                redisTemplate.opsForValue().set(s,"1",1000*60*60*24*30);
                return JsonData.buildSuccess(token);
            }
            //密码错误
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
        } else {
            //未注册
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
    }

    @Override
    public UserVO findUserDetail() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", loginUser.getId()));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    private boolean checkUnique(String mail) {
        return true;
    }

    private void userRegisterInitTask(UserDO userDO) {

    }
}
