package gray.bingo.common.exceptions;

/**
 * BINGO框架系统异常
 */
public class BingoException extends RuntimeException {


    public BingoException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return "BingoException{ message : " + this.getMessage() + "}";
    }
}