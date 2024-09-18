package gray.bingo.common.Enums;

import lombok.Getter;

@Getter
public enum ExceptionCodeEnum {
    SUCCESS(20000, "成功"),
    BINGO_ERROR(20001, "框架层错误"),
    ;

    // 响应状态码
    private final Integer code;
    // 响应信息
    private final String message;

    ExceptionCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}