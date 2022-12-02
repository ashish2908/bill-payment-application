package com.online.payment.ExceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int statusCode;
    private String message;

}
