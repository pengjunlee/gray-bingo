package gray.bingo.common.exceptions;

import gray.bingo.common.inface.ExceptionService;

/**
 * BINGO框架系统异常
 */
public class BingoException extends RuntimeException {

    private Integer code;

    public BingoException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public BingoException(ExceptionService exceptionService) {
        super(exceptionService.getMessage());
        this.code = exceptionService.getCode();
    }

    @Override
    public String toString() {
        return "BingoException{ code : " + this.getCode() + ", message : " + this.getMessage() + "}";
    }
}