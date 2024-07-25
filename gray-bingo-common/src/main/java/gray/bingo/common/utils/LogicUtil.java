package gray.bingo.common.utils;

import gray.bingo.common.exceptions.BingoException;

import java.util.Collection;
import java.util.function.Consumer;

public class LogicUtil {

    /**
     * 根据入参真假，分别进行不同的操作
     * @param b
     * @return
     */
    public static BranchHandler trueOrFalse(boolean b) {
        return (trueHandler, falseHandler) -> {
            if (b) {
                trueHandler.run();
            } else {
                falseHandler.run();
            }
        };
    }

    /**
     * 判断字符串是否为空，分别进行不同的操作
     *
     * @param str
     * @return
     */
    public static PresentOrNullHandler presentOrBlank(String str) {
        return (consumer, emptyHandler) -> {
            if (null == str || str.length() == 0) {
                emptyHandler.run();
            } else {
                consumer.accept(str);
            }
        };
    }

    /**
     * 判断集合是否为空，分别进行不同的操作
     *
     * @param collection
     * @return
     */
    public static PresentOrNullHandler presentOrEmpty(Collection collection) {
        return (consumer, emptyHandler) -> {
            if (null == collection || collection.size() == 0) {
                emptyHandler.run();
            } else {
                consumer.accept(collection);
            }
        };
    }

    /**
     * 如果参数为false抛出异常
     *
     * @param b
     * @return
     */
    public static ThrowExceptionHanlder mustTrue(boolean b) {
        return (errorMsg) -> {
            if (!b) throw new BingoException(errorMsg);
        };
    }

    /**
     * 分支处理
     */
    @FunctionalInterface
    public interface BranchHandler {

        /**
         * 分支操作
         *
         * @param trueHandler  为true时要进行的操作
         * @param falseHandler 为false时要进行的操作
         */
        void trueOrFalseHandle(Runnable trueHandler, Runnable falseHandler);
    }


    /**
     * 空与非空分支判断
     *
     * @param <T>
     */
    @FunctionalInterface
    public interface PresentOrNullHandler<T extends Object> {

        public void presentOrNullHandle(Consumer<? extends T> consumer, Runnable emptyHandler);
    }

    /**
     * 抛异常
     */
    @FunctionalInterface
    public interface ThrowExceptionHanlder {

        public void throwException(String errorMsg);
    }
}
