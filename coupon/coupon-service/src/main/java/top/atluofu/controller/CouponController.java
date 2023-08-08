package top.atluofu.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.atluofu.enums.CouponCategoryEnum;
import top.atluofu.service.CouponService;
import top.atluofu.utils.JsonData;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
@RestController
@RequestMapping("/api/coupon/v1")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @ApiOperation("分页查询优惠券")
    @GetMapping("page_coupon")
    public JsonData pageCouponList(
            @ApiParam(value = "page") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "size") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Map<String, Object> pageMap = couponService.pageCouponActivity(page, size);
        return JsonData.buildSuccess(pageMap);
    }


    @GetMapping("/add/promotion/{coupon_id}")
    public JsonData addPromotionCoupon(@ApiParam(value = "couponID", required = true) @PathVariable("coupon_id") long couponId) {
        JsonData jsonData = couponService.addCoupon(couponId, CouponCategoryEnum.PROMOTION.toString());
        return JsonData.buildSuccess();
    }

}

