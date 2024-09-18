package gray.bingo.common.utils;

import gray.bingo.common.Enums.base.BaseIntEnum;

import java.util.HashMap;
import java.util.Map;

public class EnumUtil {

    private static final Map<String, Class<? extends BaseIntEnum>> BASE_INT_ENUMS = new HashMap<>();


    public static Map<String, Class<? extends BaseIntEnum>> getIntMap() {
        return BASE_INT_ENUMS;
    }
}
