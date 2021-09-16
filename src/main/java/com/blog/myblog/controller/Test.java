package com.blog.myblog.controller;
import com.blog.myblog.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class Test {

    private final static Logger LOG = LoggerFactory.getLogger(Test.class);

    @Resource
    private TestService testService;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/say")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/list")
    public List<Test> getTestList() {
        return testService.list();
    }

    @GetMapping("/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable Long key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }


    @RequestMapping("/redis/set/{key}/{value}")
    public String setRedis(@PathVariable  Long key,@PathVariable String value){
        redisTemplate.opsForValue().set(key, value, 3600 * 24, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object getRedis(@PathVariable Long key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }
}
