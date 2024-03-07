package gray.bingo.common.Enums.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二月菌
 * @version 1.0
 * @date 2023-11-19 16:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnumOption<T> {

    private T value;

    private String name;
}
