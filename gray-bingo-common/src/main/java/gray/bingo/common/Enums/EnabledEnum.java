package gray.bingo.common.Enums;

import gray.bingo.common.Enums.base.BaseIntEnum;
import gray.bingo.common.Enums.base.EnumOption;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author 二月菌
 * @version 1.0
 * @date 2023-11-19 16:04
 */
@AllArgsConstructor
public enum EnabledEnum implements BaseIntEnum {

    ENABLED(1, "√"),
    DISABLED(0, "×"),
    ;

    private Integer value;

    private String name;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        BaseIntEnum baseEnum = BaseIntEnum.getByValue(EnabledEnum.class, 1);
        System.out.println(baseEnum);
        Map<Integer, String> map = BaseIntEnum.toMap(EnabledEnum.class);
        System.out.println(map);
        List<EnumOption<Integer>> list = BaseIntEnum.toList(EnabledEnum.class);
        System.out.println(list);
    }
}
