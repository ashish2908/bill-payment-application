package com.online.payment.controller;

import com.online.payment.entity.TransactionHistory;
import com.online.payment.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public List<TransactionHistory> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
