package com.demo.web.controller;

import com.demo.model.CurrencyType;
import com.demo.service.CurrencyConverterService;
import com.demo.service.Impl.CurrencyConverterServiceImpl;
import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CurrencyConverterServiceTest {

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public CurrencyConverterService currencyConverterService() {
            CurrencyConverterService currencyConverterService = new CurrencyConverterServiceImpl();
            return currencyConverterService;
        }
    }

    @Autowired
    CurrencyConverterService currencyConverterService;

    @Test
    public void givenCurrencyTypeWhenStringThanEquals() {
        Currency currency = currencyConverterService.findCurrency(CurrencyType.USD);

        Assert.assertEquals(Currency.USD, currency);
    }

    @Test
    public void givenCurrencyTypeWhenGetRateThanEquals() throws CurrencyConverterException {
        Currency usd = currencyConverterService.findCurrency(CurrencyType.USD);
        Currency eur = currencyConverterService.findCurrency(CurrencyType.EUR);
        CurrencyConverter currencyConverter = new BankUaCom(Currency.USD, Currency.EUR);

        Float inCurrencyTo = currencyConverter.convertCurrency(1f);
        Float rate = currencyConverterService.getRate(usd, eur);

        Assert.assertEquals(rate, inCurrencyTo);
    }

}
