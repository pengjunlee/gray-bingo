package com.pengjunlee.bingo.utils;

public class StringUtil {
    public static boolean isBlank(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}
