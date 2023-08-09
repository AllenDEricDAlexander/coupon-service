package top.atluofu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.atluofu.req.CartItemReq;
import top.atluofu.service.CartService;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.CartVO;

/**
 * @ClassName: CartController
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-21:03
 * @Version: 1.0
 */
@Api("cart-service")
@RestController
@RequestMapping("/api/cart/v1")
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @ApiOperation("add cart")
    @PostMapping("add")
    public JsonData addToCart(@ApiParam("CartItemReq") @RequestBody CartItemReq cartItemReq) {
        cartService.addToCart(cartItemReq);
        return JsonData.buildSuccess();
    }

    @ApiOperation("change cart item")
    @PostMapping("change")
    public JsonData changeCart(@ApiParam("CartItemReq") @RequestBody CartItemReq cartItemReq) {
        cartService.change(cartItemReq);
        return JsonData.buildSuccess();
    }

    @ApiOperation("clear cart")
    @PostMapping("clear")
    public JsonData clearCart() {
        cartService.clear();
        return JsonData.buildSuccess();
    }

    @ApiOperation("delete cart item")
    @PostMapping("delete/{id}")
    public JsonData deleteItem(@PathVariable("id") long id) {
        cartService.deleteItem(id);
        return JsonData.buildSuccess();
    }

    @ApiOperation("find cart")
    @PostMapping("my_cart")
    public JsonData findMyCart() {
        CartVO cartVO= cartService.getMyCart();
        return JsonData.buildSuccess(cartVO);
    }
}
