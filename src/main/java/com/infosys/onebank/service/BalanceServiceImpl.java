package com.infosys.onebank.service;


import com.infosys.onebank.resource.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private AccountService accountService;

    public Balance getBalance(String forAccount) {
        return accountService.getAccountBalance(forAccount);
    }

    public Balance getBalanceDefaultAccount() {
        return getBalance(accountService.getDefaultAccount());
    }

    public Balance getBalanceAllAccounts() {
        List<String> myAccounts = accountService.listMyAccounts();
        double amount = 0;
        String currency = "";
        for(String account : myAccounts) {
            Balance balance = getBalance(account);
            amount = amount + balance.getAmount();
            currency = balance.getCurrency();
        }
        return new Balance("Consolidated", currency, amount);
    }

}


