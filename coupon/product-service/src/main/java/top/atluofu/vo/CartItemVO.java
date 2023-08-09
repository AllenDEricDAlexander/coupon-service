package top.atluofu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: CartItemVO
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-09Day-18:38
 * @Version: 1.0
 */
@Data
public class CartItemVO {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("by_num")
    private Integer byNum;

    @JsonProperty("product_title")
    private String productTitle;

    @JsonProperty("product_image")
    private String productImage;

    private BigDecimal amount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return amount.multiply(BigDecimal.valueOf(byNum));
    }
}
