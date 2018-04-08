package com.demo.exceptions;

import java.math.BigDecimal;

public class NotEnoughFundsException extends BankException {

    public NotEnoughFundsException(Integer id, BigDecimal balance, BigDecimal amount, String message) {
        super(message + " id : " + id + " balance : " + " amount : " + amount);
    }

}
