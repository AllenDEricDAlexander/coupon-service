package top.atluofu.service;

import top.atluofu.model.BannerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.atluofu.vo.BannerVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
public interface BannerService extends IService<BannerDO> {

    List<BannerVO> getList();
}
