package com.blog.myblog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.blog")
@MapperScan("com.blog.myblog.mapper")
public class MyBlogApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyBlogApplication.class, args);
    }

}
