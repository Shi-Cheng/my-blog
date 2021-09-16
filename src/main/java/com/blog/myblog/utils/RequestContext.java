package com.blog.myblog.utils;
import java.io.Serializable;

public class RequestContext implements Serializable {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getRemoteAddr() {
        return threadLocal.get();
    }

    public static void setRemoteAddr(String remoteAddr) {
        RequestContext.threadLocal.set(remoteAddr);
    }

}
