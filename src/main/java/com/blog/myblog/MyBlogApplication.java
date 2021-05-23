package com.blog.myblog;

import org.omg.CORBA.Environment;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class MyBlogApplication {

//    private static final Logger LOG = (Logger) LoggerFactory.getLogger(MyBlogApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(MyBlogApplication.class, args);
//        SpringApplication app = new SpringApplication(MyBlogApplication.class);

//        Environment env = (Environment) app.run(args).getEnvironment();
//
//        LOG.info("启动成功");
    }

}
