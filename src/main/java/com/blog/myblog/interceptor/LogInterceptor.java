package com.blog.myblog.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* 拦截器：Spring框架特有的，常用于登录校验，权限校验，请求日志打印 /login
*/
@Component
public class LogInterceptor implements HandlerInterceptor {

 private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

 @Override
 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     // 打印请求信息
     // 中间的代码跟前面的过滤器差不多，这里同样是打印日志请求，然后生成了一个开始时间
     LOG.info("------------- LogInterceptor 开始 -------------");
     LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
     LOG.info("远程地址: {}", request.getRemoteAddr());

     long startTime = System.currentTimeMillis();
     request.setAttribute("requestStartTime", startTime);
     // 如果返回false，表示业务就结束了
     // 比如增加一个登录校验，当校验出未登录或者登录超时的时候，就return false，后面的业务逻辑就不再执行
     return true;
 }

 @Override
 public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
     long startTime = (Long) request.getAttribute("requestStartTime");
     LOG.info("------------- LogInterceptor 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
 }
}
