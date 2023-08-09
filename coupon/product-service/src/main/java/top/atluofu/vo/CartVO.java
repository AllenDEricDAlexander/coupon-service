package top.atluofu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: CartVO
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-18:38
 * @Version: 1.0
 */
@Data
public class CartVO {

    @JsonProperty("cart_items")
    private List<CartItemVO> cartItems;

    @JsonProperty("total_num")
    private Integer totalNum;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("real_pay_price")
    private BigDecimal realPayPrice;

    public Integer getTotalNum() {
        return cartItems.stream().mapToInt(CartItemVO::getByNum).sum();
    }

    public BigDecimal getTotalPrice() {
        BigDecimal bigDecimal = new BigDecimal("0");
        for (CartItemVO cartItem : cartItems) {
            bigDecimal = bigDecimal.add(cartItem.getTotalAmount());
        }
        return bigDecimal;
    }
}
