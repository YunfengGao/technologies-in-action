package com.github.yunfeng.util;

public class EnvUtils {
    private EnvUtils() {
        throw new RuntimeException();
    }

    public static String getEnv(String name) {
        return System.getenv(name);
    }
}
