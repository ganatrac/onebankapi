package com.infosys.onebank.service;


import com.infosys.onebank.exception.InvalidAPICallException;
import com.infosys.onebank.utils.APIExceptionUtils;
import com.infosys.onebank.utils.JsonParserUtils;
import com.infosys.onebank.utils.RestHeaderUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.infosys.onebank.OneBankConstants.*;


/**
 * Created by chirag.ganatra on 7/9/2018.
 */
@Service
public class ValidationService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoginService loginService;


    public boolean validateAccountId(String accountId) {
        String accountURI = BASE_URI + "obp/v3.0.0/my/banks/" + BANK_ID + "/accounts/" + accountId + "/account";
        ResponseEntity<String> responseEntity = restTemplate.exchange(accountURI, HttpMethod.GET, new HttpEntity<String>(getRequestHeaders()), String.class);
        if(responseEntity.getStatusCode().isError()) {
            throw APIExceptionUtils.createInvalidAPIException(responseEntity);
        }
        return true;
    }

    private HttpHeaders getRequestHeaders() {
        return RestHeaderUtils.createHeaders(loginService.getLoginToken());
    }


}
