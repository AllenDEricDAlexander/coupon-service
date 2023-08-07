package top.atluofu.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.enums.SendCodeEnum;
import top.atluofu.model.UserDO;
import top.atluofu.mapper.UserMapper;
import top.atluofu.req.UserRegisterRequest;
import top.atluofu.service.MailService;
import top.atluofu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.utils.CommonUtil;
import top.atluofu.utils.JsonData;

import java.util.Date;

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

    public UserServiceImpl(MailService mailService, UserMapper userMapper) {
        this.mailService = mailService;
        this.userMapper = userMapper;
    }

    @Override
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
        if(checkUnique(userDO.getMail())){
            int rows = userMapper.insert(userDO);
            log.info("rows:{},register :{}", rows, userDO.toString());
            userRegisterInitTask(userDO);
            return JsonData.buildResult(BizCodeEnum.SUCCESS);
        }else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }

    private boolean checkUnique(String mail) {
        return true;
    }

    private void userRegisterInitTask(UserDO userDO) {

    }
}
