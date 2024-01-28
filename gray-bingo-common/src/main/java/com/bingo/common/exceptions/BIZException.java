package com.bingo.common.exceptions;

/**
 * 业务异常
 */
public class BIZException extends RuntimeException{

    private String code;

    public BIZException(String msg){
        super(msg);

    }

    public BIZException(String code, String msg){
        super(msg);
        this.code = code;
    }
}