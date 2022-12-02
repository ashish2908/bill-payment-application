package com.online.payment.entity;

import com.online.payment.dto.CustomerDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@Component
public class Customer {
    @Id
    @Column(name = "email_id", nullable = false)
    @Email
    private String emailId;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "wallet_id", nullable = false, unique = true)
    private Wallet wallet = new Wallet();

    public CustomerDTO toDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmailId(emailId);
        customerDTO.setWalletBalance(wallet.getBalance());
        return customerDTO;
    }
}