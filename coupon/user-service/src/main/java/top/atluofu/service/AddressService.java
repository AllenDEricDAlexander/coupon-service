package top.atluofu.service;

import top.atluofu.model.AddressDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.req.AddressAddReq;
import top.atluofu.vo.AddressVO;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
public interface AddressService extends IService<AddressDO> {
    AddressVO detail(long id);

    int add(AddressAddReq addressAddReq);

    int delete(int addressId);

    List<AddressVO> userAllAddressList();
}
