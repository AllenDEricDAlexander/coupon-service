package top.atluofu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.atluofu.enums.BizCodeEnum;
import top.atluofu.model.AddressDO;
import top.atluofu.req.AddressAddReq;
import top.atluofu.service.AddressService;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.AddressVO;

import java.util.List;

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
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @ApiOperation(value = "select address by id")
    @GetMapping("find/{address_id}")
    public JsonData detail(@ApiParam(value = "addressID", required = true) @PathVariable("address_id") long addressId) {
        AddressVO detail = addressService.detail(addressId);
        if (detail != null) {
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildError(-1, "address not found");
        }
    }

    @ApiOperation("address add")
    @PostMapping("add")
    public JsonData add(@ApiParam("address obj") @RequestBody AddressAddReq addressAddReq) {
        int row = addressService.add(addressAddReq);
        return JsonData.buildSuccess();
    }

    @ApiOperation("address delete by id")
    @PostMapping("delete/{address_id}")
    public JsonData delete(@ApiParam(value = "address_id", required = true) @PathVariable("address_id") int addressId) {
        int row = addressService.delete(addressId);
        return row == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.ADDRESS_DEL_FAIL);
    }

    @ApiOperation("findUserAllAddress")
    @PostMapping("list")
    public JsonData findUserAllAddress() {
        List<AddressVO> list = addressService.userAllAddressList();
        return JsonData.buildSuccess(list);
    }
}

