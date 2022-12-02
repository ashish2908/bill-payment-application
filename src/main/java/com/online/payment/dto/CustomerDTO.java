package com.online.payment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private String emailId;
    private Long walletBalance;

}
