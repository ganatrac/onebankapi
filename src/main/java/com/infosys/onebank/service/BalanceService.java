package com.infosys.onebank.service;


import com.infosys.onebank.resource.Balance;

public interface BalanceService {
    Balance getBalance(String forAccount);
    Balance getBalanceAllAccounts();
    Balance getBalanceDefaultAccount();
}
