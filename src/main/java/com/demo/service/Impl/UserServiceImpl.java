package com.demo.service.Impl;

import com.demo.model.Account;
import com.demo.model.CurrencyType;
import com.demo.model.Role;
import com.demo.model.User;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${balanceUSD}")
    private double balanceUSD;
    @Value("${balanceEUR}")
    private double balanceEUR;
    @Value("${balanceRUR}")
    private double balanceRUR;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setAccounts(getDefaultAccounts(user));
        userRepository.save(user);
    }

    private Set<Account> getDefaultAccounts(User user) {
        Set<Account> accounts = new HashSet<>();

        setDefaultAccountFromProperties(user, accounts, CurrencyType.USD, balanceUSD);

        setDefaultAccountFromProperties(user, accounts, CurrencyType.EUR, balanceEUR);

        setDefaultAccountFromProperties(user, accounts, CurrencyType.RUR, balanceRUR);

        return accounts;
    }

    private void setDefaultAccountFromProperties(User user, Set<Account> accounts, CurrencyType usd, double balance) {
        Account account = new Account();
        account.setUser(user);
        account.setType(usd);
        account.setBalance(balance);
        accounts.add(account);
    }

    @Override
    public Double getBalanceByCurrencyType(User user, CurrencyType type) {
        double balance = 0;
        for (Account acc : user.getAccounts()) {
            if (type.equals(acc.getType())) {
                 balance = acc.getBalance();
            }
        }
        return balance;
    }
}
