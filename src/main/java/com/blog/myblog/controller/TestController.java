package com.blog.myblog.controller;

import com.blog.myblog.domain.Test;
import com.blog.myblog.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private TestService testService;

    @Value("${test.hello:TEST}")
    private String  testHello;

    @GetMapping("/hello")
    public String hello() {
        return "hello world:" + testHello;
    }

    @PostMapping("/hello/post")
    public String hellopost(String name){
        return "hello world, post" + name;
    }

    @GetMapping("/get/list")
    public List<Test> list() {
        return testService.list();
    }
}
