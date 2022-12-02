package com.online.payment.ExceptionHandler;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException() {
        super("You do not have sufficient balance to pay the bill");
    }

}
