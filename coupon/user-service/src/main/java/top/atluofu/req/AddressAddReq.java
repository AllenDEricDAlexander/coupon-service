package top.atluofu.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: AddressAddReq
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-16:07
 * @Version: 1.0
 */
@Data
@ApiModel(value = "address obj", description = "address obj")
public class AddressAddReq {

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @ApiModelProperty(value = "status", example = "0")
    @JsonProperty("default_status")
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    @ApiModelProperty(value = "receive_name", example = "allen")
    @JsonProperty("receive_name")
    private String receiveName;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "phone", example = "12312341234")
    private String phone;

    /**
     * 省/直辖市
     */
    @ApiModelProperty(value = "province", example = "123")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "city", example = "1234")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "region", example = "12345")
    private String region;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "detail_address", example = "qweqwe")
    @JsonProperty("detail_address")
    private String detailAddress;
}
