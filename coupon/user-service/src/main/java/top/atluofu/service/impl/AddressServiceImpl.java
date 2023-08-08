package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.atluofu.enums.AddressStatusEnum;
import top.atluofu.interceptor.LoginInterceptor;
import top.atluofu.model.AddressDO;
import top.atluofu.mapper.AddressMapper;
import top.atluofu.model.LoginUser;
import top.atluofu.req.AddressAddReq;
import top.atluofu.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.vo.AddressVO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 电商-公司收发货地址表 服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressDO> implements AddressService {

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressVO detail(long id) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id).eq("user_id",loginUser.getId()));
        if (addressDO == null) {
            return null;
        }
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressDO, addressVO);
        return addressVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int add(AddressAddReq addressAddReq) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        BeanUtils.copyProperties(addressAddReq, addressDO);
        addressDO.setUserId(loginUser.getId());
        //是否有默认收货地址
        if (addressDO.getDefaultStatus() == AddressStatusEnum.DEFAULT_STATUS.getStatus()) {
            AddressDO defaultAddressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()).eq("default_status", AddressStatusEnum.DEFAULT_STATUS.getStatus()));
            if (defaultAddressDO != null) {
                //修改为非默认地址
                defaultAddressDO.setDefaultStatus(AddressStatusEnum.COMMON_STATUS_ENUM.getStatus());
                addressMapper.update(defaultAddressDO, new QueryWrapper<AddressDO>().eq("id", defaultAddressDO.getId()));
            }
        }
        return addressMapper.insert(addressDO);
    }

    @Override
    public int delete(int addressId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        return addressMapper.delete(new QueryWrapper<AddressDO>().eq("id", addressId).eq("user_id", loginUser.getId()));
    }

    @Override
    public List<AddressVO> userAllAddressList() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        List<AddressDO> list = addressMapper.selectList(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()));
        return list.stream().map(obj -> {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(obj, addressVO);
            return addressVO;
        }).collect(Collectors.toList());
    }
}
