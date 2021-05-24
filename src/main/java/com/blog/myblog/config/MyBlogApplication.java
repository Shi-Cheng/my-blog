package com.blog.myblog.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.blog")
@SpringBootApplication
public class MyBlogApplication {

//    private static final Logger LOG = LoggerFactory.getLogger(MyBlogApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(MyBlogApplication.class, args);
//        SpringApplication app = new SpringApplication(MyBlogApplication.class);
//        Environment env = app.run(args).getEnvironment();
//        LOG.info("启动成功！！");
//        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
