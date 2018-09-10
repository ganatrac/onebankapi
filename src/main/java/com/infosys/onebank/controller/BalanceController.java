package com.infosys.onebank.controller;

import com.infosys.onebank.resource.Balance;
import com.infosys.onebank.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.infosys.onebank.OneBankConstants.*;
/**
 * Created by chirag.ganatra on 9/7/2018.
 */
@RestController
@RequestMapping("/"+API_ROOT+"/"+API_VERSION)
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping(path="/balance", produces = "application/json")
    @ResponseBody
    public Balance balance() {
        return balanceService.getBalanceDefaultAccount();
    }
}
