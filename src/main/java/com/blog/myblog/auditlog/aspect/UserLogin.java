package com.blog.myblog.auditlog.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLogin {

    String des() default "";
}
