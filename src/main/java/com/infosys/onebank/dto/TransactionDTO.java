package com.infosys.onebank.dto;

/**
 * Created by chirag.ganatra on 9/10/2018.
 */
public class TransactionDTO {

    private String fromAccount;
    private double amount;
    private String toAccount;

    public TransactionDTO(double amount, String toAccount) {
        this.amount = amount;
        this.toAccount = toAccount;
    }

    public TransactionDTO(String fromAccount, double amount, String toAccount) {
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.toAccount = toAccount;
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
