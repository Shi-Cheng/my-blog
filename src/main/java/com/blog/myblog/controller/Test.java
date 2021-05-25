package com.blog.myblog.controller;

import com.blog.myblog.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class Test {

    @Resource
    private TestService testService;

    @GetMapping("/say")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/list")
    public List<Test> getTestList() {
        return testService.list();
    }
}
