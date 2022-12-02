package com.online.payment.ExceptionHandler;

public class InvalidEmailException extends Exception {

    public InvalidEmailException(String msg) {
        super("Please provide valid email address");
    }
}
