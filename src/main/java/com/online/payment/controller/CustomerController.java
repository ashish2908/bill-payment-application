package com.online.payment.controller;

import com.online.payment.ExceptionHandler.CustomerAlreadyExistsException;
import com.online.payment.ExceptionHandler.InvalidEmailException;
import com.online.payment.dto.CustomerDTO;
import com.online.payment.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("registration")
    public CustomerDTO registerCustomer(@RequestParam("email") String email) throws Exception {
        boolean customerExists = customerService.checkIfCustomeAlreadyExists(email);
        if(!customerExists) {
            try {
                return customerService.createCustomer(email).toDTO();
            } catch (ValidationException ex) {
                throw new InvalidEmailException(email);
            }
        }
        throw new CustomerAlreadyExistsException(email);
    }

    @PutMapping("/wallet/add")
    public void addFundsToWallet(@RequestParam("email") String email, @RequestParam("amount") Long amountInRupees) throws Exception {
        boolean customerExists = customerService.checkIfCustomeAlreadyExists(email);
        if(!customerExists) {
            throw new InvalidEmailException(email);
        }
        customerService.addFundsToWallet(email, amountInRupees);
    }

    @GetMapping
    public CustomerDTO getCustomer(@RequestParam("email") String email) throws InvalidEmailException {
        boolean customerExists = customerService.checkIfCustomeAlreadyExists(email);
        if(!customerExists) {
            throw new InvalidEmailException(email);
        }
        return customerService.getCustomer(email);
    }


}
