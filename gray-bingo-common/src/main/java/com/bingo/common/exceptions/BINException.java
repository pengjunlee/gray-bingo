package com.bingo.common.exceptions;

/**
 * BINGO异常
 */
public class BINException extends RuntimeException {

    private String code;

    public BINException(String msg) {
        super(msg);

    }

    public BINException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}