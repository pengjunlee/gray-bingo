package com.pengjunlee.bingo.utils;

import java.util.Objects;
import java.util.StringJoiner;

public class StringUtil {
    public static boolean isBlank(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }


    public static String join(Object[] array, String delimiter) {
        return array == null ? null : join((Object[])array, delimiter, 0, array.length);
    }

    public static String join(Object[] array, String delimiter, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else if (endIndex - startIndex <= 0) {
            return "";
        } else {
            StringJoiner joiner = new StringJoiner(toStringOrEmpty(delimiter));
            for(int i = startIndex; i < endIndex; ++i) {
                joiner.add(toStringOrEmpty(array[i]));
            }
            return joiner.toString();
        }
    }
    private static String toStringOrEmpty(Object obj) {
        return Objects.toString(obj, "");
    }
}
