package com.demo.service;

import com.demo.model.CurrencyType;
import com.demo.model.User;

import java.math.BigDecimal;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    BigDecimal getBalanceByCurrencyType(User user, CurrencyType type);
}
