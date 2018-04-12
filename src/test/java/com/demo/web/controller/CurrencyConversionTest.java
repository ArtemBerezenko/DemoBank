package com.demo.web.controller;

import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import org.junit.Assert;
import org.junit.Test;

public class CurrencyConversionTest {

    @Test
    public void givenCurrencyCodeWhenStringThanExist() {
        Currency usd = Currency.USD;

        Assert.assertNotNull(usd);
        Assert.assertEquals(usd.name(), "USD");
        Assert.assertEquals(usd.getFullName(), "United States Dollar");
    }

    @Test
    public void givenAmountWhenConversionThenNotNull() throws CurrencyConverterException {
        CurrencyConverter currencyConverter = new BankUaCom(Currency.USD, Currency.EUR);
        Float inEuro = currencyConverter.convertCurrency(1f);
        Assert.assertNotNull(inEuro);
    }
}
