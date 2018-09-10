package com.infosys.onebank.service;


import com.infosys.onebank.resource.Balance;

public interface BalanceService {
    Balance getMyBalance(String forAccount);
    Balance getMyBalanceAllAccounts();
    Balance getBalanceDefaultAccount();
}
