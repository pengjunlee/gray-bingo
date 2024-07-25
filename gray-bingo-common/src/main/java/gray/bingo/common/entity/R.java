package gray.bingo.common.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class R<T> {

    public static final String SUCCESS = "success";

    // 状态码
    private int code;

    // 响应消息
    private String msg;

    // 响应内容
    private T data;

    public static <T> R<T> success(T data) {
        R<T> response = new R<>();
        response.setCode(HttpStatus.OK.value());
        response.setMsg(SUCCESS);
        response.setData(data);
        return response;
    }

    public static <T> R<T> error(int code, String message) {
        R<T> response = new R<>();
        response.setCode(code);
        response.setMsg(message);
        response.setData(null);
        return response;
    }    
}