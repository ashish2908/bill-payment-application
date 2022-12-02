package com.online.payment.controller;

import com.online.payment.entity.TransactionHistory;
import com.online.payment.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void getAllTransactions() {
        List<TransactionHistory> list = new ArrayList();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BDDMockito.lenient().when(transactionRepository.findAll()).thenReturn(list);

        List<TransactionHistory> response = transactionController.getAllTransactions();
        Assertions.assertEquals(0, response.size());


    }
}