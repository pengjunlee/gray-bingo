package com.bingo.common.utils;

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

    /**
     * 字符串左侧填充
     * @param str
     * @param filledChar
     * @param len
     * @return
     */
    public static String leftFill(String str, char filledChar, int len) {
        return fill(str, filledChar, len, true);
    }

    /**
     * 字符串右侧填充
     * @param str
     * @param filledChar
     * @param len
     * @return
     */
    public static String rightFill(String str, char filledChar, int len) {
        return fill(str, filledChar, len, false);
    }

    public static String fill(String str, char filledChar, int len, boolean isPre) {
        int strLen = str.length();
        if (strLen > len) {
            return str;
        } else {
            String filledStr = repeat(filledChar, len - strLen);
            return isPre ? filledStr.concat(str) : str.concat(filledStr);
        }
    }

    public static String repeat(char c, int count) {
        if (count <= 0) {
            return "";
        } else {
            char[] result = new char[count];

            for(int i = 0; i < count; ++i) {
                result[i] = c;
            }
            return new String(result);
        }
    }

    private static String toStringOrEmpty(Object obj) {
        return Objects.toString(obj, "");
    }
}
