package com.blog.myblog.controller;

import com.blog.myblog.auditlog.aspect.UserLogin;
import com.blog.myblog.domain.User;
import com.blog.myblog.request.*;
import com.blog.myblog.response.*;
import com.blog.myblog.service.UserService;
import com.blog.myblog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Value("${token.key}")
    private String tokenKey;

    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;
    
    @Resource
    private MD5Util md5Util;

    @Resource
    private RedisUtil redisUtil;
    

    @GetMapping("/list")
    public CommonResponse<PageResponse<UserQueryResponse>> list(@Valid  UserQueryRequest req) {
        CommonResponse<PageResponse<UserQueryResponse>> response = new CommonResponse<>();
        PageResponse<UserQueryResponse> list = userService.list(req);
        response.setData(list);
        return response;
    }

    @PostMapping("/delete")
    public CommonResponse<DeleteRequest> delete(@RequestBody  DeleteRequest req) {
        CommonResponse<DeleteRequest> commonResponse = new CommonResponse<>();
        userService.delete(req);
        return  commonResponse;
    }

    @PostMapping("/add")
    public CommonResponse<User> save(@Valid @RequestBody UserSaveRequest req) {
        CommonResponse<User> commonResponse = new CommonResponse<>();

        userService.save(req);
        return  commonResponse;
    }

    @PostMapping("/login")
    @UserLogin
    public CommonResponse<TokenResponse> login(@Valid @RequestBody UserLoginRequest req) {
        CommonResponse<TokenResponse> commonResponse = new CommonResponse<>();
        UserLoginResponse loginResponse = userService.login(req);
        /**
         * 设置token
         */
        Long token = snowFlake.nextId();
        LOG.info("loginResponse:{}", loginResponse);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token.toString());
        redisUtil.setRedis(token, loginResponse);
        commonResponse.setData(tokenResponse);
        return  commonResponse;
    }

    @GetMapping("/info/{info}")
    public CommonResponse getInfo(@PathVariable Long info) {
        CommonResponse commonResponse = new CommonResponse<>();
        Object userRedis = redisUtil.getRedis(info);

        User user = CopyUtil.copy(userRedis, User.class);
        UserLoginResponse userInfo = userService.getUserInfo(user);
        commonResponse.setData(userInfo);

        return  commonResponse;
    }

    @PostMapping("/reset")
    public CommonResponse resetPassword(@Valid @RequestBody UserResetPasswordRequest req) {
        CommonResponse commonResponse = new CommonResponse<>();
        userService.resetPassword(req);
        return  commonResponse;
    }

    @GetMapping("/logout/{token}")
    public CommonResponse logout(@PathVariable String token) {
        CommonResponse response = new CommonResponse();
        redisTemplate.delete(token);
        LOG.info("从redis中删除的token值为：{}", token);
        return  response;
    }

    @GetMapping("/getImgCode")
    public CommonResponse getImgCode() {
        CommonResponse response = new CommonResponse();
        String imgCode = CodeUtil.generateVerifyCode(4);

        int weight = 130, height = 50;
        String imgPic = null;

        try {
            imgPic = CodeUtil.createImage(weight, height, imgCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> code = new HashMap<>(3);

        LOG.info("imgCode：{}", imgCode);
        code.put("code", HexStringUtil.toHexString(Base64.getDecoder().decode(imgCode.toLowerCase())));
        code.put("img", imgPic);
        code.put("createTime", String.valueOf(System.currentTimeMillis()));
        response.setData(code);
        return  response;
    }

    @PostMapping("/checkImgCode")
    public CommonResponse checkImgCode(@RequestBody Map<String, String> req) {
        CommonResponse response = new CommonResponse();
        String code = req.get("code");
        Long codeTime = Long.valueOf(req.get("createTime"));
        String imgcode = req.get("imgcode");
        Date date = new Date();
        int timeout = 1000 * 60;
        if (StringUtils.isEmpty(imgcode) || code == null || !(HexStringUtil.toHexString(Base64.getDecoder().decode(imgcode.toLowerCase())).equals(code))) {
            response.setMessage("验证码错误，请重新输入！");
        } else if ((date.getTime() - codeTime) / timeout > 1) {
            response.setMessage("验证码已失效，请重新输入！");
        } else {
            response.setMessage("验证通过！");
        }
            return response;
    }

}
