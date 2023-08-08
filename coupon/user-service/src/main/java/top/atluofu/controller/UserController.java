package top.atluofu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import top.atluofu.component.FileService;
import top.atluofu.enums.BizCodeEnum;
import top.atluofu.req.UserLoginRequest;
import top.atluofu.req.UserRegisterRequest;
import top.atluofu.service.UserService;
import top.atluofu.utils.JsonData;
import top.atluofu.vo.UserVO;

import java.util.Map;

/**
 * <p>
 * 前端控制器
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
     * <p>
     * 默认文件大小 1M,超过会报错
     *
     * @param file
     * @return
     */
    @ApiOperation("用户头像上传")
    @PostMapping(value = "upload")
    public JsonData uploadHeaderImg(@ApiParam(value = "文件上传", required = true) @RequestPart("file") MultipartFile file) {
        String result = fileService.uploadUserHeadImg(file);
        return result != null ? JsonData.buildSuccess(result) : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

    @ApiOperation("user register")
    @PostMapping("register")
    public JsonData register(@ApiParam(value = "user register object") @RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    /**
     * 登录
     *
     * @return JsonData vJsonData
     */
    @PostMapping("login")
    @ApiOperation("user login")
    public JsonData login(@ApiParam(value = "user login object") @RequestBody UserLoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("refresh token")
    @ApiOperation("refresh token")
    public JsonData refreshToken(Map<String, Object> s) {
        // todo 去redis找token 找到 解密，调用jwt生成新的token 存储redis 返回前端
        return null;
    }

    @PostMapping("detail")
    @ApiOperation("user detail")
    public JsonData detail() {
        UserVO userVO = userService.findUserDetail();
        return JsonData.buildSuccess(userVO);
    }


}

