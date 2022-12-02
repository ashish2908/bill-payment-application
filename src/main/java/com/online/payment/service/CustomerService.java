package com.online.payment.service;

import com.online.payment.dto.CustomerDTO;
import com.online.payment.entity.Customer;
import com.online.payment.entity.TransactionHistory;
import com.online.payment.entity.Wallet;
import com.online.payment.repository.CustomerRepository;
import com.online.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    private Customer customer;

    public CustomerService(CustomerRepository customerRepository, TransactionRepository transactionRepository, Customer customer) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.customer = customer;
    }

    public Customer createCustomer(String email) {
        customer.setWallet(new Wallet());
        customer.setEmailId(email.toLowerCase());
        customer = customerRepository.saveAndFlush(customer);
        return customer;
    }

    public boolean checkIfCustomeAlreadyExists(String email) {
        return customerRepository.findById(email.toLowerCase()).isPresent();
    }

    public void addFundsToWallet(String email, Long amountInRupees) {
        Customer customer = customerRepository.findById(email.toLowerCase()).get();
        Wallet wl = customer.getWallet();
        Long existingBalance = wl.getBalance();
        wl.setBalance(existingBalance + amountInRupees);
        customerRepository.saveAndFlush(customer);

        TransactionHistory t = new TransactionHistory();
        t.setSender(email);
        t.setRecipient("wallet_"+wl.getId());
        t.setAmounts(amountInRupees);

        transactionRepository.saveAndFlush(t);
    }

    public CustomerDTO getCustomer(String email) {
        return customerRepository.findById(email.toLowerCase()).get().toDTO();
    }
}
