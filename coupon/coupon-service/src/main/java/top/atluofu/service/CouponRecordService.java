package top.atluofu.service;

import top.atluofu.model.CouponRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.model.CouponRecordMessage;
import top.atluofu.req.LockCouponRecordRequest;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.CouponVO;
import top.atluofu.vo.RecordVO;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-08
 */
public interface CouponRecordService extends IService<CouponRecordDO> {

    Map<String, Object> page(int page, int size);

    RecordVO findById(long recordId);

    JsonData lockCouponRecords(LockCouponRecordRequest recordRequest);

    boolean releaseCouponRecord(CouponRecordMessage recordMessage);
}
