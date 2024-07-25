package gray.bingo.common.entity;

import gray.bingo.common.Enums.BingoCodeEnum;
import lombok.Data;

@Data
public class R<T> {

    public static final String SUCCESS = "success";

    // 状态码
    private int code;

    // 响应消息
    private String message;

    // 响应内容
    private T data;

    public static <T> R<T> ok(T data) {
        R<T> response = new R<>();
        response.setCode(BingoCodeEnum.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> R<T> ok(int code ,T data) {
        R<T> response = new R<>();
        response.setCode(code);
        response.setData(data);
        return response;
    }

    public static <T> R<T> error(int code, String message) {
        R<T> response = new R<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}