package com.example.pricenotifierui.Exception;

public class ApiExecutionException extends RuntimeException {
    public ApiExecutionException(String msg) {
        super(msg);
    }

    ;

    public ApiExecutionException(String msg, Exception cause) {
        super(msg, cause);
    }

}
