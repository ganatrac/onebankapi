package com.infosys.onebank.dto;

/**
 * Created by chirag.ganatra on 9/10/2018.
 */
public class Transaction {

    private String fromAccount;
    private double amount;
    private String toAccount;
    private String description;

    public Transaction(double amount, String toAccount, String description) {
        this.amount = amount;
        this.toAccount = toAccount;
        this.description = description;
    }

    public Transaction(String fromAccount, double amount, String toAccount, String description) {
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.toAccount = toAccount;
        this.description = description;
    }

    public Transaction(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
}
