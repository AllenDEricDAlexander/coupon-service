package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.enums.CouponCategoryEnum;
import top.atluofu.enums.CouponPublishEnum;
import top.atluofu.enums.CouponStateEnum;
import top.atluofu.exception.BizException;
import top.atluofu.interceptor.LoginInterceptor;
import top.atluofu.mapper.CouponRecordMapper;
import top.atluofu.model.CouponDO;
import top.atluofu.mapper.CouponMapper;
import top.atluofu.model.CouponRecordDO;
import top.atluofu.model.LoginUser;
import top.atluofu.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.utils.CommonUtil;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.CouponVO;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
@Service
@Slf4j
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

    private final CouponMapper couponMapper;

    private final CouponRecordMapper couponRecordMapper;

    private final RedisTemplate redisTemplate;

    private final RedissonClient redissonClient;

    public CouponServiceImpl(CouponMapper couponMapper, CouponRecordMapper couponRecordMapper, RedisTemplate redisTemplate, RedissonClient redissonClient) {
        this.couponMapper = couponMapper;
        this.couponRecordMapper = couponRecordMapper;
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {
        Page<CouponDO> couponPageInfo = new Page<>(page, size);
        Page<CouponDO> couponPage = couponMapper.selectPage(couponPageInfo, new QueryWrapper<CouponDO>()
                .eq("publish", CouponPublishEnum.PUBLISH)
                .orderByDesc("create_time")
        );
        HashMap<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", couponPage.getTotal());
        pageMap.put("total_page", couponPage.getPages());
        pageMap.put("current_data", couponPage.getRecords().stream().map(this::beanProcess).collect(Collectors.toList()));
        return pageMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public JsonData addCoupon(long couponId, String couponCategory) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String lockKey = "lock:coupon:" + couponId;

        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        try {
            CouponDO couponDO = couponMapper.selectOne(new QueryWrapper<CouponDO>().eq("id", couponId)
                    .eq("category", couponCategory)
                    .eq("publish", CouponPublishEnum.PUBLISH));
            this.couponCheck(couponDO, loginUser.getId());

            CouponRecordDO couponRecordDO = new CouponRecordDO();
            BeanUtils.copyProperties(couponDO, couponRecordDO);
            couponRecordDO.setCreateTime(new Date());
            couponRecordDO.setUseState(CouponStateEnum.NEW.name());
            couponRecordDO.setUserId(loginUser.getId());
            couponRecordDO.setUserName(loginUser.getName());
            couponRecordDO.setCouponId(couponId);
            couponRecordDO.setId(null);
            // 高并发下扣减劵库存，采用乐观锁,当前stock做版本号,延伸多种防止超卖的问题,一次只能领取1张
            int rows = couponMapper.reduceStock(couponId, couponDO.getStock());
            if (rows == 1) {
                //库存扣减成功才保存
                couponRecordMapper.insert(couponRecordDO);
            } else {
                log.warn("发放优惠券失败:{},用户:{}", couponDO, loginUser);
                throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
            }
        } finally {
            lock.unlock();
        }
        return JsonData.buildSuccess();
    }

    private CouponVO beanProcess(CouponDO obj) {
        CouponVO couponVO = new CouponVO();
        BeanUtils.copyProperties(obj, couponVO);
        return couponVO;
    }


    private void couponCheck(CouponDO couponDO, long userId) {

        //优惠券不存在
        if (couponDO == null) {
            throw new BizException(BizCodeEnum.COUPON_NO_EXITS);
        }
        //库存不足
        if (couponDO.getStock() <= 0) {
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }
        //是否在领取时间范围
        long time = CommonUtil.getCurrentTimestamp();
        long start = couponDO.getStartTime().getTime();
        long end = couponDO.getEndTime().getTime();
        if (time < start || time > end) {
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }
        //用户是否超过限制
        int recordNum = couponRecordMapper.selectCount(new QueryWrapper<CouponRecordDO>()
                .eq("coupon_id", couponDO.getId())
                .eq("user_id", userId));

        if (recordNum >= couponDO.getUserLimit()) {
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }
    }
}
