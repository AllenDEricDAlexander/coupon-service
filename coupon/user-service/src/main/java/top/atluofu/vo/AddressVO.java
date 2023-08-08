package top.atluofu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName: AddressVO
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-16:28
 * @Version: 1.0
 */
@Data
public class AddressVO {

    @JsonProperty("default_status")
    private Integer defaultStatus;

    @JsonProperty("receive_name")
    private String receiveName;

    private String phone;

    private String province;

    private String city;

    private String region;

    @JsonProperty("detail_address")
    private String detailAddress;
}
