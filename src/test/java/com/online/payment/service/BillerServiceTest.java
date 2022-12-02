package com.online.payment.service;

import com.online.payment.ExceptionHandler.InsufficientBalanceException;
import com.online.payment.dto.BillDTO;
import com.online.payment.entity.Biller;
import com.online.payment.entity.Customer;
import com.online.payment.repository.BillerRepository;
import com.online.payment.repository.CustomerRepository;
import com.online.payment.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class BillerServiceTest {

    private BillerRepository billerRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    List<Biller> billerList;

    private Biller biller;
    private BillerService billerService;
    private Customer c;

    private String email = "bagulashish29@gmail.com";

    @BeforeEach
    public void setup(){
        biller = new Biller();
        billerRepository = Mockito.mock(BillerRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);

        billerService = new BillerService(billerRepository, customerRepository, transactionRepository);

        biller.setId(1L);
        biller.setBalance(50L);
        biller.setMothlyBill(100L);

        billerList = new ArrayList();
        billerList.add(biller);

        c = new Customer();
        c.setEmailId(email);
        c.getWallet().setId(1L);
        c.getWallet().setBalance(150L);

        BDDMockito.lenient().when(billerRepository.findAll()).thenReturn(billerList);
        BDDMockito.lenient().when(customerRepository.findById(email)).thenReturn(Optional.of(c));
        BDDMockito.lenient().when(billerRepository.findById(1L)).thenReturn(Optional.of(biller));


    }

    @DisplayName("JUnit test for getting all billers")
    @Test
    public void testGettingBillers(){
        List<Biller> response = billerService.getAllBillers();

        Assertions.assertTrue(response.size() == 1);
        Assertions.assertTrue(response.get(0).getId() == 1);
    }

    @DisplayName("JUnit test for getting monthly bill")
    @Test
    public void testGetMonthlyBillsToPay(){
        BillDTO response = billerService.getMonthlyBillToPay(1L);
        Assertions.assertEquals(100L, response.getAmount());
    }

    @DisplayName("JUnit test for paying bill")
    @Test
    public void testPayBill() throws InsufficientBalanceException {
        billerService.payBill(1L, email);
        Assertions.assertEquals(50, c.getWallet().getBalance());
        try {
            billerService.payBill(1L, email);
        } catch(Exception e) {
            Assertions.assertEquals("You do not have sufficient balance to pay the bill",e.getMessage());
        }
    }

}