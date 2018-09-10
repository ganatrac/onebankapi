package com.infosys.onebank.resource;

import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by chirag.ganatra on 9/10/2018.
 */
public class Transaction implements Comparable<Transaction>{
    private String transactionId;
    private String myAccount;
    private String counterPartyName;
    private double amount;
    private String currency;
    private String transactionDate;
    private double currentBalance;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    public Transaction(String transactionId, String myAccount, String counterPartyName,
                         double amount, String currency, String transactionDate, double currentBalance) {
        this.transactionId = transactionId;
        this.myAccount = myAccount;
        this.counterPartyName = counterPartyName;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = sdf.format(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                .parseDateTime(transactionDate)
                .toGregorianCalendar()
                .getTime());
        this.currentBalance = currentBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getMyAccount() {
        return myAccount;
    }

    public String getCounterPartyName() {
        return counterPartyName;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    @Override
    public int compareTo(Transaction o) {
        try {
            return sdf.parse(o.getTransactionDate()).compareTo(sdf.parse(o.getTransactionDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
