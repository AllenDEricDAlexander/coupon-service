package top.atluofu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import top.atluofu.component.FileService;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.req.UserRegisterRequest;
import top.atluofu.service.UserService;
import top.atluofu.utils.JsonData;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author atluofu
 * @since 2023-08-06
 */
@Api(tags = "user model")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    private final FileService fileService;

    private final UserService userService;

    public UserController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    /**
     * 上传用户头像
     *
     * 默认文件大小 1M,超过会报错
     *
     * @param file
     * @return
     */
    @ApiOperation("用户头像上传")
    @PostMapping(value = "upload")
    public JsonData uploadHeaderImg(@ApiParam(value = "文件上传",required = true) @RequestPart("file") MultipartFile file){
        String result = fileService.uploadUserHeadImg(file);
        return result != null?JsonData.buildSuccess(result):JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

    @ApiOperation("user register")
    @PostMapping("register")
    public JsonData register(@ApiParam(value = "user register object")@RequestBody UserRegisterRequest userRegisterRequest){
        return userService.register(userRegisterRequest);
    }

}

