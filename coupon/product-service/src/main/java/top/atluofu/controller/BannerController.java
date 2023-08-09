package top.atluofu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.atluofu.service.BannerService;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.BannerVO;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Api("banner-service")
@RestController
@RequestMapping("/api/banner/v1")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @ApiOperation("banner_list")
    @GetMapping("list")
    public JsonData list() {
        List<BannerVO> bannerVOList = bannerService.getList();
        return JsonData.buildSuccess(bannerVOList);
    }

}

