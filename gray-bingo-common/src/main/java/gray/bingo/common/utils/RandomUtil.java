package gray.bingo.common.utils;

import java.util.UUID;

public class RandomUtil {

    /**
     * 获取不带“-”符号的UUID串
     * @return
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8)
                + uuid.substring(9, 13)
                + uuid.substring(14, 18)
                + uuid.substring(19, 23)
                + uuid.substring(24, 36);
    }
}