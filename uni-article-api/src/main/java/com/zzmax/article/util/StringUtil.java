package com.zzmax.article.util;

import java.util.Random;

public class StringUtil {
    private final static int LENGTH = 6;

    /**
     * ★获取六位随机数短信验证码
     * k @return String
     */
    public static String getVerifyCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

}
