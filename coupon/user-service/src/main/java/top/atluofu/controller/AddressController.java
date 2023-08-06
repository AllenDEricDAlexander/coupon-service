package top.atluofu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.atluofu.model.AddressDO;
import top.atluofu.service.AddressService;
import top.atluofu.utils.JsonData;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
@Api(tags = "address moedl")
@RestController
@RequestMapping("/api/address/v1")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "select address by id")
    @GetMapping("find/{address_id}")
    public JsonData detail(@ApiParam(value = "addressID", required = true) @PathVariable("address_id") long addressId) {
        AddressDO detail = addressService.detail(addressId);
        if (detail == null) {
            return JsonData.buildSuccess();
        }else {
            return JsonData.buildError(-1,"address not found");
        }
    }

}

