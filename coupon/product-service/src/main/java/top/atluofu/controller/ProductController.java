package top.atluofu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.atluofu.model.ProductDO;
import top.atluofu.req.LockProductRequest;
import top.atluofu.service.ProductService;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.ProductVO;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@RestController
@Api("product-service")
@RequestMapping("/api/product/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(("list-page"))
    @PostMapping("page")
    public JsonData listPage(
            @ApiParam(value = "page") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "size") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Map<String, Object> pageMap = productService.pageCouponActivity(page, size);
        return JsonData.buildSuccess(pageMap);
    }

    @ApiOperation("product detail")
    @GetMapping("detail/{product_id}")
    public JsonData detail(@ApiParam(value = "productId", required = true) @PathVariable("product_id") long productId) {
        ProductVO byId = productService.findDetailById(productId);
        return JsonData.buildSuccess(byId);
    }

    /**
     * 商品库存锁定
     * @return
     */
    @ApiOperation("商品库存锁定")
    @PostMapping("lock_products")
    public JsonData lockProducts(@ApiParam("商品库存锁定") @RequestBody LockProductRequest lockProductRequest){


        JsonData jsonData = productService.lockProductStock(lockProductRequest);

        return jsonData;
    }

}

