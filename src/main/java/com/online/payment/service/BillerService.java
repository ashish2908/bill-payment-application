package com.online.payment.service;

import com.online.payment.ExceptionHandler.InsufficientBalanceException;
import com.online.payment.dto.BillDTO;
import com.online.payment.entity.Biller;
import com.online.payment.entity.Customer;
import com.online.payment.entity.TransactionHistory;
import com.online.payment.repository.BillerRepository;
import com.online.payment.repository.CustomerRepository;
import com.online.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillerService {

    private final BillerRepository billerRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public BillerService(BillerRepository billerRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.billerRepository = billerRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Biller> getAllBillers() {
        return billerRepository.findAll();
    }

    public BillDTO getMonthlyBillToPay(Long id) {
        BillDTO billDTO = new BillDTO();
        billDTO.setAmount(billerRepository.findById(id).get().getMothlyBill());
        LocalDate currentDate = LocalDate.now();
        billDTO.setMonth(currentDate.getMonth().name());
        return billDTO;
    }

    public void payBill(Long id, String email) throws InsufficientBalanceException {
        Biller biller = billerRepository.findById(id).get();
        Customer customer = customerRepository.findById(email).get();

        Long existingBalance = customer.getWallet().getBalance();
        if(existingBalance < biller.getMothlyBill())
            throw new InsufficientBalanceException();


        biller.setBalance(biller.getMothlyBill());
        billerRepository.saveAndFlush(biller);

        customer.getWallet().setBalance(existingBalance - biller.getMothlyBill());
        customerRepository.saveAndFlush(customer);

        TransactionHistory t = new TransactionHistory();
        t.setSender(email);
        t.setRecipient("biller_"+id);
        t.setAmounts(biller.getMothlyBill());

        transactionRepository.saveAndFlush(t);

    }
}
