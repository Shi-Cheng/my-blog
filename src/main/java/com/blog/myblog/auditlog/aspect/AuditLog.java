package com.blog.myblog.auditlog.aspect;


import com.blog.myblog.domain.SystemLog;
import com.blog.myblog.domain.User;
import com.blog.myblog.mapper.SystemLogMapper;
import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.TokenResponse;
import com.blog.myblog.utils.Constants;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.RedisUtil;
import com.blog.myblog.utils.SnowFlake;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class AuditLog {

    private static final Logger LOG = LoggerFactory.getLogger(AuditLog.class);

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SystemLogMapper systemLogMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 登录日志切点
     */
    @Pointcut("@annotation(com.blog.myblog.auditlog.aspect.UserLogin)")
    public void userLoginPointCut(){}


    @org.aspectj.lang.annotation.Around("userLoginPointCut()")
    public Object userLoginLog(ProceedingJoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String methodName = Constants.TypeCode.LOGIN;
        SystemLog systemLog = new SystemLog();
        try {
            Object result = joinPoint.proceed();
            CommonResponse commonResponse = (CommonResponse)result;
            if (commonResponse.getSuccess()) {
                TokenResponse tokenResponse = (TokenResponse)commonResponse.getData();
                String token = tokenResponse.getToken();
                Object redisUser = redisUtil.getRedis(Long.parseLong(token));
                User user = CopyUtil.copy(redisUser, User.class);
                systemLog.setId(snowFlake.nextId());
                systemLog.setCreatTime(new Date());
                systemLog.setUsername(user.getName());
                systemLog.setModule(methodName);
                systemLog.setIp(request.getRemoteAddr());
                LOG.info("登录模块日志:{}", systemLog);
                systemLogMapper.insert(systemLog);
            }
            return  result;
        } catch (Exception e) {
            return  null;
        }
    }
}
