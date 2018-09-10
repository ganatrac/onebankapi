package com.infosys.onebank.service;


import com.infosys.onebank.resource.Balance;
import com.infosys.onebank.utils.JsonParserUtils;
import com.infosys.onebank.utils.RestHeaderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.infosys.onebank.OneBankConstants.BANK_ID;
import static com.infosys.onebank.OneBankConstants.BASE_URI;

@Service
public class AccountServiceImpl implements AccountService {

    static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private RestTemplate restTemplate;

    public List<String> listMyAccounts() {
        return getMyAccounts();
    }

    private List<String> getMyAccounts() {
        String accountURI = BASE_URI + "obp/v2.0.0/banks/" + BANK_ID + "/accounts";
        logger.info("sending request to " + accountURI);

        ResponseEntity<String> responseEntity = restTemplate.exchange(accountURI, HttpMethod.GET, new HttpEntity<String>(getRequestHeaders()), String.class);

        JSONArray responseJson = JsonParserUtils.parseArray(responseEntity.getBody());
        List<String> accountList = new ArrayList<String>();
        for(int i = 0; i < responseJson.size(); i++) {
            accountList.add(((JSONObject)responseJson.get(i)).get("id").toString());
        }
        return accountList;


    }

    public String getDefaultAccount(){
       return getMyAccounts().stream().findFirst().orElse("");
    }

    public Balance getAccountBalance(String account) {
        String accountURI = BASE_URI + "obp/v3.1.0/my/banks/" + BANK_ID + "/accounts/" + account + "/account";

        ResponseEntity<String> responseEntity = restTemplate.exchange(accountURI, HttpMethod.GET, new HttpEntity<String>(getRequestHeaders()), String.class);

        JSONObject responseJson = JsonParserUtils.parse(responseEntity.getBody());
        return new Balance(
                account,
                ((JSONObject)responseJson.get("balance")).get("currency").toString(),
                Double.parseDouble(((JSONObject)responseJson.get("balance")).get("amount").toString())
        );

    }

    private HttpHeaders getRequestHeaders() {
        return RestHeaderUtils.createHeaders(loginService.getLoginToken());
    }

}
