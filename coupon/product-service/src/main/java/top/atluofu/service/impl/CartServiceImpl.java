package top.atluofu.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.atluofu.constant.CacheKey;
import top.atluofu.interceptor.LoginInterceptor;
import top.atluofu.model.LoginUser;
import top.atluofu.req.CartItemReq;
import top.atluofu.service.CartService;
import top.atluofu.service.ProductService;
import top.atluofu.vo.CartItemVO;
import top.atluofu.vo.CartVO;
import top.atluofu.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName: CartServiceImpl
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-21:04
 * @Version: 1.0
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final StringRedisTemplate redisTemplate;

    private final ProductService productService;

    public CartServiceImpl(StringRedisTemplate redisTemplate, ProductService productService) {
        this.redisTemplate = redisTemplate;
        this.productService = productService;
    }

    @Override
    public void addToCart(CartItemReq cartItemRequest) {

        Long productId = cartItemRequest.getProductId();
        int buyNum = cartItemRequest.getBuyNum();
        //获取购物车
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();

        Object cacheObj = myCart.get(productId);
        String result = "";

        if (cacheObj != null) {
            result = (String) cacheObj;
        }
        if (StringUtils.isBlank(result)) {
            //不存在则新建一个购物项
            CartItemVO cartItem = new CartItemVO();
            ProductVO productVO = productService.findDetailById(productId);
            cartItem.setAmount(productVO.getAmount());
            cartItem.setByNum(buyNum);
            cartItem.setProductId(productId);
            cartItem.setProductImage(productVO.getCoverImg());
            cartItem.setProductTitle(productVO.getTitle());
            myCart.put(productId, JSON.toJSONString(cartItem));
        } else {
            //存在则新增数量
            CartItemVO cartItem = JSON.parseObject(result, CartItemVO.class);
            cartItem.setByNum(cartItem.getByNum() + buyNum);
            myCart.put(productId, JSON.toJSONString(cartItem));
        }
    }

    @Override
    public void clear() {
        String cartKey = getCartKey();
        redisTemplate.delete(cartKey);
    }

    @Override
    public CartVO getMyCart() {
        List<CartItemVO> cartItemVOList = buildCartItem(false);
        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);
        return cartVO;
    }

    @Override
    public void deleteItem(long id) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        myCartOps.delete(id);
    }

    @Override
    public void change(CartItemReq cartItemReq) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        String o = (String) myCartOps.get(cartItemReq.getProductId());
        CartItemVO cartItemVO = JSON.parseObject(o, CartItemVO.class);
        cartItemVO.setByNum(cartItemReq.getBuyNum());
        myCartOps.put(cartItemReq.getProductId(), cartItemVO);
    }

    private List<CartItemVO> buildCartItem(boolean latestPrice) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        List<Object> values = myCartOps.values();
        List<CartItemVO> cartItemVOList = new ArrayList<>();
        List<Long> productIdList = new ArrayList<>();
        for (Object value : values) {
            CartItemVO cartItemVO = JSON.parseObject((String) value, CartItemVO.class);
            cartItemVOList.add(cartItemVO);
            productIdList.add(cartItemVO.getProductId());
        }
        if (latestPrice) {
            setProductLatestPrice(cartItemVOList, productIdList);
        }
        return cartItemVOList;
    }

    private void setProductLatestPrice(List<CartItemVO> cartItemVOList, List<Long> productIdList) {
        List<ProductVO> productVOList = productService.findProductByIdBatch(productIdList);
        Map<Long, ProductVO> collect = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));
        cartItemVOList.stream().forEach(cartItemVO -> {
            ProductVO productVO = collect.get(cartItemVO.getProductId());
            BeanUtils.copyProperties(cartItemVO, productVO);
        });
    }

    /**
     * 抽取我的购物车通用方法
     *
     * @return
     */
    private BoundHashOperations<String, Object, Object> getMyCartOps() {
        String cartKey = getCartKey();
        return redisTemplate.boundHashOps(cartKey);
    }


    /**
     * 获取购物车的key
     *
     * @return
     */
    private String getCartKey() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        return String.format(CacheKey.CART_KEY, loginUser.getId());
    }

}
