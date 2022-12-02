package com.online.payment.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillDTOTest {

    @Test
    void getMonth() {
        BillDTO billDTO = new BillDTO();
        billDTO.setMonth("January");

        Assertions.assertEquals("January", billDTO.getMonth());
    }

    @Test
    void getAmount() {

        BillDTO billDTO = new BillDTO();
        billDTO.setAmount(100L);

        Assertions.assertEquals(100L, billDTO.getAmount());
    }
}