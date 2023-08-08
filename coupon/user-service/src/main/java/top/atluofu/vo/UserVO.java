package top.atluofu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * @ClassName: UserVO
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-15:57
 * @Version: 1.0
 */
@Data
public class UserVO {
    private Long id;

    private String name;

    @JsonProperty("head_img")
    private String headImg;

    private String slogan;

    private Integer sex;

    private Integer points;

    private String mail;

}
