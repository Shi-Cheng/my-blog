package com.blog.myblog.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class UtilController {

    private static final Logger LOG = LoggerFactory.getLogger(UtilController.class);

    @Resource
    private RedisTemplate redisTemplate;


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
