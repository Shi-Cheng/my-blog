package com.blog.myblog.utils;

import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Encoder;
import org.bouncycastle.util.encoders.HexEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HexStringUtil {
    private static final Encoder ENCODER = new HexEncoder();

    public static String toHexString(byte[] var0) {
        return toHexString(var0, 0, var0.length);
    }

    public static String toHexString(byte[] var0, int var1, int var2) {
        byte[] var3 = encode(var0, var1, var2);
        return Strings.fromByteArray(var3);
    }

    public static byte[] encode(byte[] var0, int var1, int var2) {
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();

        try {
            ENCODER.encode(var0, var1, var2, var3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return var3.toByteArray();
    }

}
