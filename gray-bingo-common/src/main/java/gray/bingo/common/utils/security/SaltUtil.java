package gray.bingo.common.utils.security;

import java.security.SecureRandom;

/**
 * 获取随机盐值
 *
 * @author graython
 * @since 0.0.1
 */
public class SaltUtil {

    /**
     * 安全随机类
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 获取随机盐值
     * @return 加盐
     */
    public static String randomSalt() {
        byte[] bytes = new byte[15];
        RANDOM.nextBytes(bytes);
        return Base64Util.encrypt(bytes);
    }
}
