package com.infosys.onebank.service;


import com.infosys.onebank.resource.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private AccountService accountService;

    public Balance getMyBalance(String forAccount) {
        return accountService.getAccountBalance(forAccount);
    }

    public Balance getBalanceDefaultAccount() {
        return getMyBalance(accountService.getDefaultAccount());
    }

    public Balance getMyBalanceAllAccounts() {
        List<String> myAccounts = accountService.listMyAccounts();
        double amount = 0;
        String currency = "";
        for(String account : myAccounts) {
            Balance balance = getMyBalance(account);
            amount = amount + balance.getAmount();
            currency = balance.getCurrency();
        }
        return new Balance("Consolidated", currency, amount);
    }

}


