package gray.bingo.common.exceptions;

/**
 * BINGO异常
 */
public class BingoException extends RuntimeException {

    private String code;

    public BingoException(String msg) {
        super(msg);

    }

    public BingoException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}