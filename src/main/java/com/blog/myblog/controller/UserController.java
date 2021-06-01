package com.blog.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.myblog.domain.User;
import com.blog.myblog.request.*;
import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.response.UserLoginResponse;
import com.blog.myblog.response.UserQueryResponse;
import com.blog.myblog.service.UserService;
import com.blog.myblog.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    public CommonResponse<PageResponse<UserQueryResponse>> list(UserQueryRequest req) {
        CommonResponse<PageResponse<UserQueryResponse>> response = new CommonResponse<>();
        PageResponse<UserQueryResponse> list = userService.list(req);
        response.setContent(list);
        return response;
    }

    @PostMapping("/delete")
    public CommonResponse<DeleteRequest> delete(DeleteRequest req) {
        CommonResponse<DeleteRequest> commonResponse = new CommonResponse<>();
        userService.delete(req);
        return  commonResponse;
    }

    @PostMapping("/add")
    public CommonResponse<User> save(UserSaveRequest req) {
        CommonResponse<User> commonResponse = new CommonResponse<>();
        userService.save(req);
        return  commonResponse;
    }

    @PostMapping("/login")
    public CommonResponse<UserLoginResponse> login(UserLoginRequest req) {
        CommonResponse<UserLoginResponse> commonResponse = new CommonResponse<>();
        UserLoginResponse loginResponse = userService.login(req);

        Long token = snowFlake.nextId();
        loginResponse.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(loginResponse), 3600 * 24, TimeUnit.SECONDS);
        commonResponse.setContent(loginResponse);
        return  commonResponse;
    }

    @PostMapping("/reset")
    public CommonResponse resetPassword(UserResetPasswordRequest req) {
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
