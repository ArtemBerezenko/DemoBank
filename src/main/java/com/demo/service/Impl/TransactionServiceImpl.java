package com.demo.service.Impl;

import com.demo.exceptions.NoSuchCurrencyTypeAccount;
import com.demo.exceptions.NotEnoughFundsException;
import com.demo.model.Account;
import com.demo.model.User;
import com.demo.repository.AccountDAO;
import com.demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private AccountDAO accountDAO;

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    @Transactional
    public void buy(User user, String currencyFrom, String currencyTo, BigDecimal amountFrom, BigDecimal amountTo) throws NoSuchCurrencyTypeAccount, NotEnoughFundsException {
        Account accountFrom = getAccountByCurrencyType(user, currencyFrom);
        logger.info("INFO: accountFrom before: " + accountFrom.getBalance());
        accountFrom.withdraw(amountFrom);
        this.accountDAO.updateAccount(accountFrom);
        logger.info("INFO: accountFrom after: " + accountFrom.getBalance());
        Account accountTo = getAccountByCurrencyType(user, currencyTo);
        logger.info("INFO: accountTo before: " + accountTo.getBalance());
        accountTo.deposit(amountTo);
        this.accountDAO.updateAccount(accountTo);
        logger.info("INFO: accountTo after: " + accountTo.getBalance());
    }

    private Account getAccountByCurrencyType(User user, String type) throws NoSuchCurrencyTypeAccount {
        Account account = new Account();
        for (Account acc : user.getAccounts()) {
            if (type.equals(acc.getType().toString())) {
                account = acc;
            }
        }
        if (account.getType() == null) {
            throw new NoSuchCurrencyTypeAccount("User doesn't have such currency");
        } else {
            return account;
        }
    }
}
