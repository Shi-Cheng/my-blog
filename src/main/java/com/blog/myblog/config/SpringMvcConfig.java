/**
 * 增加一个拦截器，注入 LogInterceptor 拦截器
 */
package com.blog.myblog.config;
import com.blog.myblog.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/redis/**",
                        "/doc/all",
                        "/doc/vote"
                ); // 把所有的接口都要去打印接口耗时，但当校验login接口时，此时不用去拦截
    }
}
