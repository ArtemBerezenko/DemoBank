package com.demo.service;

import com.demo.model.CurrencyType;
import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;

public class CurrencyConverterServiceImpl implements CurrencyConverterService{

    @Override
    public Float getRate(CurrencyType currencyFrom, CurrencyType currencyTo) {
        CurrencyConverter currencyConverter = null;
        try {
            currencyConverter = new BankUaCom(currencyFrom, currencyTo);
        } catch (CurrencyConverterException e) {
            e.printStackTrace();
        }
        Float inEuro = null;
        try {
            inEuro = currencyConverter.convertCurrency(1f);
        } catch (CurrencyConverterException e) {
            e.printStackTrace();
        }

        return inEuro;
    }
}
