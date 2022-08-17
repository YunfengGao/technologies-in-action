package com.github.yunfeng.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtils {
    private static final String XX = System.getenv("xx");

    private StringUtils() {
        throw new RuntimeException();
    }

    public static int getLength(String source) {
        if (source == null) {
            return 0;
        }
        return source.length();
    }

    public static int getLength2() {
        try {
            return XX.length();
        } catch (Exception e) {
            log.error("getLength2 catch exception", e);
            throw e;
        }
    }
}
