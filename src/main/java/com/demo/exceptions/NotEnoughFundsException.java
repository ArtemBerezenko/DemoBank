package com.demo.exceptions;

public class NotEnoughFundsException extends BankException {

    public NotEnoughFundsException(Integer id, Double balance, long amount, String message) {
        super(message + " id : " + id + " balance : " + " amount : " + amount);
    }

}
