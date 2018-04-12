package com.demo.service;

import com.demo.exceptions.NoSuchCurrencyTypeAccount;
import com.demo.exceptions.NotEnoughFundsException;
import com.demo.model.CurrencyType;
import com.demo.model.User;

import java.math.BigDecimal;

public interface TransactionService {
    void buy(User user, String currencyFrom, String currencyTo, BigDecimal amountFrom, BigDecimal amountTo) throws NoSuchCurrencyTypeAccount, NotEnoughFundsException;
}
