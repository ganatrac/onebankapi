package com.infosys.onebank.controller;

import com.infosys.onebank.exception.InvalidAPICallException;
import com.infosys.onebank.resource.Balance;
import com.infosys.onebank.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.infosys.onebank.OneBankConstants.*;
/**
 * Created by chirag.ganatra on 9/7/2018.
 */
@RestController
@RequestMapping("/"+API_ROOT+"/"+API_VERSION)
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping(path = "/balance", produces = "application/json")
    @ResponseBody
    public Balance balance() {
        return balanceService.getBalanceDefaultAccount();
    }

    @GetMapping(path = "/balance/{id}/account", produces = "application/json")
    public Balance balance(@PathVariable("id") String id) {
        return balanceService.getBalance(id);
    }

    @GetMapping(path = "/balance/accounts", produces = "application/json")
    public Balance balanceAll() {
        return balanceService.getBalanceAllAccounts();
    }
}
