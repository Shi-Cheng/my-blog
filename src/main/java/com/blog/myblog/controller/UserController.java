package com.blog.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.myblog.domain.User;
import com.blog.myblog.request.*;
import com.blog.myblog.response.*;
import com.blog.myblog.service.UserService;
import com.blog.myblog.utils.MD5Util;
import com.blog.myblog.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    

    @GetMapping("/list")
    public CommonResponse<PageResponse<UserQueryResponse>> list(@Valid  UserQueryRequest req) {
        CommonResponse<PageResponse<UserQueryResponse>> response = new CommonResponse<>();
        PageResponse<UserQueryResponse> list = userService.list(req);
        response.setContent(list);
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
    public CommonResponse<TokenResponse> login(@Valid @RequestBody UserLoginRequest req) {
        CommonResponse<TokenResponse> commonResponse = new CommonResponse<>();
        UserLoginResponse loginResponse = userService.login(req);

        //String md5Str = md5Util.md5(loginResponse.getId().toString(), tokenKey);
        /**
         * 设置token
         */
        //Long token = snowFlake.nextId();
        LOG.info("loginResponse:{}", loginResponse);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(loginResponse.getName());
        redisTemplate.opsForValue().set(loginResponse.getName(), JSONObject.toJSONString(tokenResponse), 3600 * 24, TimeUnit.SECONDS);
        commonResponse.setContent(tokenResponse);
        return  commonResponse;
    }

    @GetMapping("info")
    public CommonResponse<UserLoginResponse> getInfo(@RequestParam String token) {
        CommonResponse commonResponse = new CommonResponse<>();
        UserLoginResponse userInfo = userService.getUserInfo(token);
        commonResponse.setContent(userInfo);

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
}
