package top.atluofu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.atluofu.enums.BizCodeEnum;
import top.atluofu.interceptor.LoginInterceptor;
import top.atluofu.mapper.CouponRecordMapper;
import top.atluofu.model.CouponRecordDO;
import top.atluofu.model.LoginUser;
import top.atluofu.service.CouponRecordService;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.CouponVO;
import top.atluofu.vo.RecordVO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
@RestController
@RequestMapping("/api/coupon_record/v1")
public class CouponRecordController {

    @Autowired
    private CouponRecordService couponRecordService;

    @ApiOperation("分页查询我的优惠券列表")
    @GetMapping("page")
    public JsonData page(@ApiParam(value = "page") @RequestParam(value = "page", defaultValue = "1") int page,
                         @ApiParam(value = "size") @RequestParam(value = "size", defaultValue = "20") int size) {
        Map<String, Object> pageInfo = couponRecordService.page(page, size);
        return JsonData.buildSuccess(pageInfo);
    }

    @ApiOperation("查询优惠券记录信息")
    @GetMapping("/detail/{record_id}")
    public JsonData findUserCouponRecordById(@PathVariable("record_id") long recordId) {
        RecordVO couponRecordVO = couponRecordService.findById(recordId);
        return couponRecordVO == null ? JsonData.buildResult(BizCodeEnum.COUPON_NO_EXITS) : JsonData.buildSuccess(couponRecordVO);
    }


}

