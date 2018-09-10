package com.infosys.onebank.service;

import com.infosys.onebank.resource.Transaction;


import java.util.List;

public interface TransactionService {
    List<Transaction> listTransactions(String forAccount);
    List<Transaction> listTransactions(String forAccount, int count);
    String createTransaction(com.infosys.onebank.dto.Transaction transaction);
}

