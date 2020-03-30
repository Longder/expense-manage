package com.xwork.expense.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 加密工具类
 */
public class EncryptionUtil {
    private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encrypt(String source){
        return ENCODER.encode(source);
    }
}
