package com.online.payment.service;

import com.online.payment.entity.Biller;
import com.online.payment.entity.Customer;
import com.online.payment.repository.BillerRepository;
import com.online.payment.repository.CustomerRepository;
import com.online.payment.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;


    List<Biller> billerList;

    private Biller biller;
    private CustomerService customerService;
    private Customer c;

    private String email = "bagulashish29@gmail.com";

    @BeforeEach
    public void setup(){
        biller = new Biller();
        customerRepository = Mockito.mock(CustomerRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);

        c = new Customer();
        c.setEmailId(email);
        c.getWallet().setBalance(150L);
        customerService = new CustomerService(customerRepository, transactionRepository, c);

        biller.setId(1L);
        biller.setBalance(50L);
        biller.setMothlyBill(100L);

        billerList = new ArrayList();
        billerList.add(biller);

        BDDMockito.lenient().when(customerRepository.saveAndFlush(c)).thenReturn(c);
        BDDMockito.lenient().when(customerRepository.findById(email)).thenReturn(Optional.of(c));

    }

    @Test
    public void testCreateCustomer() {
        Customer response = customerService.createCustomer(email);
        Assertions.assertEquals(email, response.getEmailId());
    }

    @Test
    public void testIfCustomerExist() {
        Assertions.assertTrue(customerService.checkIfCustomeAlreadyExists(email));
    }

    @Test
    public void testGetCustomer() {
        Assertions.assertEquals(email, customerService.getCustomer(email).getEmailId());
    }

    @Test
    public void testAddingFundsToWallet() {
        customerService.addFundsToWallet(email, 1000L);
        Assertions.assertEquals(1150, c.getWallet().getBalance());
    }

}