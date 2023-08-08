package top.atluofu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName: LoginUser
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-14:06
 * @Version: 1.0
 */
@Data
public class LoginUser {

    private Long id;
    private String name;
    private String mail;
    @JsonProperty("head_img")
    private String headImg;
}
