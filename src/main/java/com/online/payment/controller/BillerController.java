package com.online.payment.controller;

import com.online.payment.ExceptionHandler.InsufficientBalanceException;
import com.online.payment.dto.BillDTO;
import com.online.payment.entity.Biller;
import com.online.payment.service.BillerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("biller")
public class BillerController {

    private final BillerService billerService;

    public BillerController(BillerService billerService) {
        this.billerService = billerService;
    }

    @GetMapping
    public List<Biller> getAllBillers() {
        return billerService.getAllBillers();
    }

    @GetMapping("{id}")
    public BillDTO getMonthlyBillToPay(@PathVariable("id") Long id) {
        return billerService.getMonthlyBillToPay(id);
    }

    @PostMapping("{id}")
    public void payBill(@PathVariable("id") Long id, @RequestParam("email") String email) throws InsufficientBalanceException {
        billerService.payBill(id, email);
    }



}
