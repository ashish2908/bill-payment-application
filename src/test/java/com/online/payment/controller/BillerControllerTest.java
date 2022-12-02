package com.online.payment.controller;

import com.online.payment.ExceptionHandler.InsufficientBalanceException;
import com.online.payment.dto.BillDTO;
import com.online.payment.service.BillerService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BillerControllerTest {

    @InjectMocks
    private BillerController billerController;

    @Mock
    private BillerService billerService;

    @Test
    void getAllBillers() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BDDMockito.lenient().when(billerService.getAllBillers()).thenReturn(new ArrayList());

        Assertions.assertEquals(0, billerController.getAllBillers().size());
    }

    @Test
    void getMonthlyBillToPay() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        BillDTO billDTO = new BillDTO();
        billDTO.setAmount(10L);

        BDDMockito.lenient().when(billerService.getMonthlyBillToPay(1L)).thenReturn(billDTO);

        Assertions.assertEquals(10L, billerController.getMonthlyBillToPay(1L).getAmount());
    }

    @Test
    void payBill() throws InsufficientBalanceException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        billerController.payBill(1L, "email");
    }
}