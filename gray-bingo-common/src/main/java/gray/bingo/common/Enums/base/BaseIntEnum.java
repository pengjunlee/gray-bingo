package gray.bingo.common.Enums.base;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Graython
 * @version 1.0
 * @date 2023-11-19 15:46
 */
public interface BaseIntEnum {

    Integer getValue();

    String getName();

    static BaseIntEnum getByValue(Class<? extends BaseIntEnum> enumClass, Integer value) {
        if (Objects.isNull(value)) {
            return null;
        }
        BaseIntEnum[] enumConstants = enumClass.getEnumConstants();
        if (Objects.isNull(enumConstants) || enumConstants.length == 0) {
            return null;
        }
        return Arrays.stream(enumConstants).filter(e -> value.equals(e.getValue())).findAny().orElse(null);
    }

    /**
     * 将枚举类转换成Map
     * @param enumClass
     * @return
     */
    static Map<Integer, String> toMap(Class<? extends BaseIntEnum> enumClass) {
        BaseIntEnum[] enumConstants = enumClass.getEnumConstants();
        if (Objects.isNull(enumConstants) || enumConstants.length == 0) {
            return Collections.emptyMap();
        }
        return Arrays.stream(enumConstants).collect(Collectors.toMap(BaseIntEnum::getValue, BaseIntEnum::getName, (v1, v2) -> v1));
    }

    /**
     * 将枚举类转换成List
     * @param enumClass
     * @return
     */
    static List<EnumOption<Integer>> toList(Class<? extends BaseIntEnum> enumClass) {
        BaseIntEnum[] enumConstants = enumClass.getEnumConstants();
        if (Objects.isNull(enumConstants) || enumConstants.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.stream(enumConstants).map(item -> EnumOption.<Integer>builder()
                .value(item.getValue())
                .name(item.getName())
                .build()).collect(Collectors.toList());
    }

}
