package top.atluofu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.atluofu.model.BannerDO;
import top.atluofu.mapper.BannerMapper;
import top.atluofu.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.atluofu.vo.BannerVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Service
@Slf4j
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerDO> implements BannerService {


    private final BannerMapper bannerMapper;

    public BannerServiceImpl(BannerMapper bannerMapper) {
        this.bannerMapper = bannerMapper;
    }

    @Override
    public List<BannerVO> getList() {
        List<BannerDO> list = bannerMapper.selectList(new QueryWrapper<BannerDO>().orderByAsc("weight"));

        return list.stream().map(obj -> {
                    BannerVO vo = new BannerVO();
                    BeanUtils.copyProperties(obj, vo);
                    return vo;
                }
        ).collect(Collectors.toList());
    }
}
