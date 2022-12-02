package com.online.payment.controller;

import com.online.payment.ExceptionHandler.CustomerAlreadyExistsException;
import com.online.payment.ExceptionHandler.InvalidEmailException;
import com.online.payment.dto.CustomerDTO;
import com.online.payment.entity.Biller;
import com.online.payment.entity.Customer;
import com.online.payment.repository.BillerRepository;
import com.online.payment.repository.CustomerRepository;
import com.online.payment.repository.TransactionRepository;
import com.online.payment.service.BillerService;
import com.online.payment.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;
    @Mock
    private CustomerService customerService;

    private Customer customer;
    private String email = "bagulashish29@gmail.com";

    @BeforeEach
    public void setup(){
        customer = new Customer();
        customer.setEmailId(email);
    }

    @Test
    void testRegisterCustomer() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BDDMockito.when(customerService.createCustomer(email)).thenReturn(customer);

        CustomerDTO customerDTO = customerController.registerCustomer(email);

        Assertions.assertEquals(email, customerDTO.getEmailId());
        Assertions.assertEquals(0L, customerDTO.getWalletBalance());

        BDDMockito.when(customerService.checkIfCustomeAlreadyExists(email)).thenReturn(true);

        try {
             customerController.registerCustomer(email);
        } catch (Exception e) {
            Assertions.assertEquals("Customer already exists with email id : "+email, e.getMessage());
        }

        BDDMockito.when(customerService.checkIfCustomeAlreadyExists(email)).thenReturn(false);

        BDDMockito.lenient().when(customerService.createCustomer(email)).thenAnswer(invocation -> { throw new ValidationException("abc msg"); });

        try {
            customerController.registerCustomer(email);
        } catch (Exception e) {
            Assertions.assertEquals("Please provide valid email address", e.getMessage());
        }


    }

    @Test
    void addFundsToWallet() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BDDMockito.when(customerService.checkIfCustomeAlreadyExists(email)).thenReturn(true);
        customerController.addFundsToWallet(email, 1000L);

        BDDMockito.when(customerService.checkIfCustomeAlreadyExists(email)).thenReturn(false);

        try {
            customerController.addFundsToWallet(email, 1000L);
        } catch(Exception e) {
            Assertions.assertEquals("Please provide valid email address", e.getMessage());
        }

    }

    @Test
    void getCustomer() throws InvalidEmailException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BDDMockito.when(customerService.checkIfCustomeAlreadyExists(email)).thenReturn(false);

        try {
            customerController.getCustomer(email);
        } catch(Exception e) {
            Assertions.assertEquals("Please provide valid email address", e.getMessage());
        }

        BDDMockito.when(customerService.checkIfCustomeAlreadyExists(email)).thenReturn(true);
        BDDMockito.when(customerService.getCustomer(email)).thenReturn(customer.toDTO());

        CustomerDTO customerDTO = customerController.getCustomer(email);
        Assertions.assertEquals(email, customerDTO.getEmailId());

    }
}