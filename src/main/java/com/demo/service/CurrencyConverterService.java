package com.demo.service;

import com.demo.model.CurrencyType;
import com.tunyk.currencyconverter.api.Currency;

import java.math.BigDecimal;

public interface CurrencyConverterService {
    Float getRate(Currency currencyFrom, Currency currencyTo);
    Currency findCurrency(CurrencyType currencyType);
    Currency findCurrency(String currencyType);
    BigDecimal calculateCurrencyCurse(BigDecimal amountFrom, Float rate);
}
