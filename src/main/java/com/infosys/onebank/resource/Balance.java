package com.infosys.onebank.resource;

/**
 * Created by chirag.ganatra on 9/7/2018.
 */
public class Balance {
    private String account;
    private double amount;
    private String currency;

    public Balance(String account, String currency, double amount) {
        this.account = account;
        this.amount = amount;
        this.currency = currency;
    }

    public String getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
