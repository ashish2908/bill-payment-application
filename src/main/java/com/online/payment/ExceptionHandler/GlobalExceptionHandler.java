package com.online.payment.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleCustomerExistsException(CustomerAlreadyExistsException ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(400);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    protected ErrorResponse handleInvalidEmailException(InvalidEmailException ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(400);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
    protected ErrorResponse handleInsufficientBalanceException(InsufficientBalanceException ex)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(400);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

}
