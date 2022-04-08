package com.example.pricenotifierui.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @Data
    public class Response {
        private String msg;

        public Response() {
        }

        ;

        public Response(String msg) {
            this.msg = msg;
        }
    }

    @ExceptionHandler(ApiExecutionException.class)
    public ResponseEntity<Response> handleException(ApiExecutionException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
