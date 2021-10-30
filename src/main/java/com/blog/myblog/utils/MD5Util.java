package com.blog.myblog.utils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5Util {
    /**
     * md5 带密钥加密
     * @param plain
     * @param key
     * @return
     */
    public static String md5(String plain, String key) {
        String textRaw = plain + key;
        String strMD5 = DigestUtils.md5DigestAsHex(textRaw.getBytes());

        return strMD5;
    }

    /**
     * md5 不带密钥加密
     * @param plain
     * @return
     */
    public static String md5WithoutKey(String plain) {
        String md5Str = DigestUtils.md5DigestAsHex(plain.getBytes());
        return md5Str;
    }

    /**
     * MD5 验证方法
     * @param plain
     * @param key
     * @param md5
     * @return
     */
    public static boolean verify(String plain, String key, String md5) {
        String md5Str = md5(plain, key);
        if (md5Str.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }
}
