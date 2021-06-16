package com.blog.myblog.auditlog.service;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class LogService {

    private String tokenHeader;

    public void put(JoinPoint joinPoint, String methodName, String module, String description) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String header = request.getHeader(tokenHeader);
        Log log = new Log();

    }
}
