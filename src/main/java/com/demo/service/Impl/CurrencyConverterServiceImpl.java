package com.demo.service.Impl;

import com.demo.model.CurrencyType;
import com.demo.service.CurrencyConverterService;
import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import com.tunyk.currencyconverter.api.CurrencyNotSupportedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("CurrencyConverterService")
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    @Override
    public Float getRate(Currency currencyFrom, Currency currencyTo) {
        CurrencyConverter currencyConverter = null;
        try {
            currencyConverter = new BankUaCom(currencyFrom, currencyTo);
        } catch (CurrencyConverterException e) {
            e.printStackTrace();
        }
        Float inCurrencyTo = null;
        try {
            inCurrencyTo = currencyConverter.convertCurrency(1f);
        } catch (CurrencyConverterException e) {
            e.printStackTrace();
        }
        return inCurrencyTo;
    }

    @Override
    public BigDecimal calculateCurrencyCurse(BigDecimal amountFrom, Float rate) {
        BigDecimal newAmount = new BigDecimal(Float.toString(rate));
        return amountFrom.multiply(newAmount);
    }

    @Override
    public Currency findCurrency(CurrencyType currencyType) {
        Currency currency = null;
        try {
            currency = Currency.fromString(currencyType.toString());
        } catch (CurrencyNotSupportedException e) {
            e.printStackTrace();
        }

        return currency;
    }

    @Override
    public Currency findCurrency(String currencyType) {
        Currency currency = null;
        try {
            currency = Currency.fromString(currencyType);
        } catch (CurrencyNotSupportedException e) {
            e.printStackTrace();
        }

        return currency;
    }


}
