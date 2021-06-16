package com.blog.myblog.auditlog.aspect;


public @interface Log {
    /**
     * 模块
     * @return
     */
    String module() default "";

    /**
     * 描述
     * @return
     */
    String description() default "";
}
