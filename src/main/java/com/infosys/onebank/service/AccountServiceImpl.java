package com.infosys.onebank.service;


import com.infosys.onebank.exception.InvalidAPICallException;
import com.infosys.onebank.resource.Balance;
import com.infosys.onebank.utils.JsonParserUtils;
import com.infosys.onebank.utils.RestHeaderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
        if(responseEntity.getStatusCode().isError()) {
            JSONObject error = JsonParserUtils.parse(responseEntity.getBody());
            throw new InvalidAPICallException(error.get("error").toString());
        }
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
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(accountURI, HttpMethod.GET, new HttpEntity<String>(getRequestHeaders()), String.class);
            if(HttpStatus.BAD_REQUEST.equals(responseEntity.getStatusCode())) {
                JSONObject error = JsonParserUtils.parse(responseEntity.getBody());
                throw new RuntimeException(error.get("error").toString());
            }
            JSONObject responseJson = JsonParserUtils.parse(responseEntity.getBody());
            return new Balance(
                    account,
                    ((JSONObject)responseJson.get("balance")).get("currency").toString(),
                    Double.parseDouble(((JSONObject)responseJson.get("balance")).get("amount").toString())
            );
        } catch (Exception e) {
            logger.error("Error while list account call", e);
        }
        return new Balance("", "", 0);
    }

    private HttpHeaders getRequestHeaders() {
        return RestHeaderUtils.createHeaders(loginService.getLoginToken());
    }

}
