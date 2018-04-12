package com.demo.exceptions;

public class NoSuchCurrencyTypeAccount extends BankException {
    public NoSuchCurrencyTypeAccount(String message) {
        super(message);
    }
}
