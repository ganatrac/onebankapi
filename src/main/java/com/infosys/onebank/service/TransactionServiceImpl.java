package com.infosys.onebank.service;


import com.infosys.onebank.dto.TransactionDTO;
import com.infosys.onebank.resource.Transaction;
import com.infosys.onebank.utils.JsonParserUtils;
import com.infosys.onebank.utils.PropertyLoader;
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
import org.springframework.util.StringUtils;
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
    @Autowired
    private AccountService accountService;


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

    public String createTransaction(TransactionDTO transactionDTO) {
        String fromAccount = StringUtils.isEmpty(transactionDTO.getFromAccount()) ? accountService.getDefaultAccount() : transactionDTO.getFromAccount();

        String counterPartyId = getCounterPartyFromAccountNo(fromAccount, transactionDTO.getToAccount());
        logger.info("Counter party id " + counterPartyId);
        return postTransaction(fromAccount, counterPartyId, transactionDTO.getAmount());
    }

    private String getCounterPartyFromAccountNo(String fromAccount, String counterPartyAccount) {
        try {
            String counterPartyURI = BASE_URI + "obp/v2.2.0/banks/" + BANK_ID + "/accounts/" + fromAccount + "/owner/counterparties";
            logger.info("Get cp id request " + counterPartyURI);
            ResponseEntity<String> responseEntity = restTemplate.exchange(counterPartyURI, HttpMethod.GET, new HttpEntity<String>(getRequestHeaders()), String.class);
            JSONObject responseJson = JsonParserUtils.parse(responseEntity.getBody());
            JSONArray counterparties = (JSONArray) responseJson.get("counterparties");

            for (int i = 0; i < counterparties.size(); i++) {
                JSONObject j = (JSONObject) counterparties.get(i);
                if (j.get("other_account_routing_address").equals(counterPartyAccount)) {
                    return (String) j.get("counterparty_id");
                }
            }
            throw new RuntimeException("Counter party not found");
        } catch (Exception e) {
            logger.error("Error get counterparty id call", e);
        }
        return "";
    }


    private String postTransaction(String fromAccount, String counterPartyId, double amount) {

        String postURI = BASE_URI + "obp/v2.1.0/banks/" + BANK_ID + "/accounts/" + fromAccount + "/owner/transaction-request-types/COUNTERPARTY/transaction-requests";
        logger.info("create transaction request " + postURI);
        String currency = PropertyLoader.getInstance().getPropertyValue("currency");
        MyTransaction t = new MyTransaction(new To(counterPartyId), new Value(currency, amount), "transaction by Alexa", "SHARED");
        logger.info("post json " + JsonParserUtils.createJson(t));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(postURI, new HttpEntity<String>(JsonParserUtils.createJson(t), getRequestHeaders()), String.class);
        JSONObject responseJson = JsonParserUtils.parse(responseEntity.getBody());
        return (String) responseJson.get("status");

    }

}

class MyTransaction {

    final To to;
    final Value value;
    final String description;
    final String charge_policy;

    public MyTransaction(To to, Value value, String description, String charge_policy) {
        this.to = to;
        this.value = value;
        this.description = description;
        this.charge_policy = charge_policy;
    }

    public To getTo() {
        return to;
    }

    public Value getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getCharge_policy() {
        return charge_policy;
    }
}

class To {
    final String counterparty_id;
    To(String counterparty_id) {
        this.counterparty_id = counterparty_id;
    }

    public String getCounterparty_id() {
        return counterparty_id;
    }


}

class Value {
    final String currency;
    final double amount;

    public Value(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }
}




