package com.infosys.onebank.service;


import com.infosys.onebank.resource.Balance;

import java.util.List;

public interface AccountService {
    List<String> listMyAccounts();
    String getDefaultAccount();
    Balance getAccountBalance(String account);
}
