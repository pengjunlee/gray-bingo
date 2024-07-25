package gray.bingo.common.Enums;

import lombok.Getter;

@Getter
public enum BingoCodeEnum {
    SUCCESS(true,20000,"成功"),
    BINGO_ERROR(false,20001,"框架层错误"),
    ;

    // 响应是否成功
    private final Boolean success;
    // 响应状态码
    private final Integer code;
    // 响应信息
    private final String message;

    BingoCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}