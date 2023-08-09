package top.atluofu.service;

import top.atluofu.req.CartItemReq;
import top.atluofu.vo.CartVO;

/**
 * @ClassName: CartService
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-21:04
 * @Version: 1.0
 */
public interface CartService {
    void addToCart(CartItemReq cartItemReq);

    void clear();

    CartVO getMyCart();

    void deleteItem(long id);

    void change(CartItemReq cartItemReq);
}
