package top.atluofu.service;

import top.atluofu.model.AddressDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 电商-公司收发货地址表 服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
public interface AddressService extends IService<AddressDO> {
public AddressDO detail(long id);
}
