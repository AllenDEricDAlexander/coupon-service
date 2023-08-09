package top.atluofu.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * <p>
 *
 * </p>
 *
 * @author atluofu
 * @since 2023-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BannerVO {


    private Integer id;

    /**
     * 图片
     */
    private String img;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 权重
     */
    private Integer weight;


}
