package com.infosys.onebank.service;


import com.infosys.onebank.resource.Transaction;
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
import java.util.stream.Collectors;

import static com.infosys.onebank.OneBankConstants.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoginService loginService;


    private HttpHeaders getRequestHeaders() {
        return RestHeaderUtils.createHeaders(loginService.getLoginToken());
    }

    private List<Transaction> getMyTransactions(String forAccount) {
        try {
            String transactionURI = BASE_URI + "/obp/v3.0.0/my/banks/" + BANK_ID + "/accounts/" + forAccount + "/transactions";
            ResponseEntity<String> responseEntity = restTemplate.exchange(transactionURI, HttpMethod.GET, new HttpEntity<String>(getRequestHeaders()), String.class);
            JSONObject responseJson = JsonParserUtils.parse(responseEntity.getBody());
            JSONArray transactionArray = (JSONArray) responseJson.get("transactions");
            List<Transaction> myTransactions = new ArrayList<Transaction>();
            for (int i = 0; i < transactionArray.size(); i++) {
                JSONObject transactionJson = (JSONObject) transactionArray.get(i);
                myTransactions.add(new Transaction(
                        transactionJson.get("id").toString(),
                        ((JSONObject) transactionJson.get("this_account")).get("id").toString(),
                        ((JSONObject) ((JSONObject) transactionJson.get("other_account")).get("holder")).get("name").toString(),
                        Double.parseDouble(((JSONObject) ((JSONObject) transactionJson.get("details")).get("value")).get("amount").toString()),
                        ((JSONObject) ((JSONObject) transactionJson.get("details")).get("value")).get("currency").toString(),
                        ((JSONObject) transactionJson.get("details")).get("completed").toString(),
                        Double.parseDouble(((JSONObject) ((JSONObject) transactionJson.get("details")).get("new_balance")).get("amount").toString())
                ));
            }
            return myTransactions;
        } catch (Exception e) {
            logger.error("Error list transactions", e);
        }
        return new ArrayList<>();
    }

    public List<Transaction> listTransactions(String forAccount, int count) {
        List<Transaction> transactions = getMyTransactions(forAccount);

        if(transactions.size() > count) {
            return transactions.stream().sorted(Transaction::compareTo).limit(count).collect(Collectors.toList());
        } else {
            return transactions;
        }
    }

    public List<Transaction> listTransactions(String forAccount) {
        return getMyTransactions(forAccount);
    }


}



