package com.infosys.onebank.controller;

import com.infosys.onebank.resource.Transaction;
import com.infosys.onebank.service.AccountService;
import com.infosys.onebank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.infosys.onebank.OneBankConstants.*;
/**
 * Created by chirag.ganatra on 9/10/2018.
 */
@RestController
@RequestMapping(path = "/"+API_ROOT+"/"+API_VERSION)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/transactions", produces = "application/json")
    public ResponseEntity transaction() {
        List<Transaction> transactionList = transactionService.listTransactions(accountService.getDefaultAccount(), 3);
        return new ResponseEntity(transactionList, HttpStatus.OK);
    }

    @GetMapping(path = "/transactions/{id}/account", produces = "application/json")
    public ResponseEntity transaction(@PathVariable("id") String id) {
        List<Transaction> transactionList = transactionService.listTransactions(id, 3);
        return new ResponseEntity(transactionList, HttpStatus.OK);
    }

}
