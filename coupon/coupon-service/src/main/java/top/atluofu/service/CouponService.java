package top.atluofu.service;

import top.atluofu.enums.CouponCategoryEnum;
import top.atluofu.model.CouponDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.utils.JsonData;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
public interface CouponService extends IService<CouponDO> {

    Map<String, Object> pageCouponActivity(int page, int size);

    JsonData addCoupon(long couponId, String couponCategory);
}
