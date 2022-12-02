package com.online.payment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDTO {
    private String month;
    private Long amount;
}
