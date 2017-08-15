package com.demo.service;

import com.demo.model.Account;
import com.demo.model.CurrencyType;
import com.demo.model.Role;
import com.demo.model.User;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
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

        Account accountUSD = new Account();
        accountUSD.setUser(user);
        accountUSD.setType(CurrencyType.USD);
        accountUSD.setBalance(balanceUSD);
        accounts.add(accountUSD);

        Account accountEUR = new Account();
        accountEUR.setUser(user);
        accountEUR.setType(CurrencyType.EUR);
        accountEUR.setBalance(balanceEUR);
        accounts.add(accountEUR);

        Account accountRUR = new Account();
        accountRUR.setUser(user);
        accountRUR.setType(CurrencyType.RUR);
        accountRUR.setBalance(balanceRUR);
        accounts.add(accountRUR);

        return accounts;
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
