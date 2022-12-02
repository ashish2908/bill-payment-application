package com.online.payment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "amount")
    private Long amounts;
}
