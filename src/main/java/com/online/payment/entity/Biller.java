package com.online.payment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Biller {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mothly_bill", nullable = false)
    private Long mothlyBill;

    @Column(name = "balance", nullable = false)
    private Long balance;


}
