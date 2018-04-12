package com.demo.model;

import com.demo.exceptions.NotEnoughFundsException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Integer id;

    @Column(name = "cur_type")
    @Enumerated(EnumType.STRING)
    private CurrencyType type;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_acc_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyType getType() {
        return type;
    }

    public void setType(CurrencyType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void deposit(BigDecimal amount) {
        this.balance = new BigDecimal(String.valueOf(balance.add(amount)));
    }

    public void withdraw(BigDecimal amount) throws NotEnoughFundsException {
        if (balance.compareTo(amount) >= 0) {
            this.balance = new BigDecimal(String.valueOf(balance.subtract(amount)));
        } else {
            throw new NotEnoughFundsException(id, balance, amount,  "Sorry, not enough money");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (type != account.type) return false;
        return balance != null ? balance.equals(account.balance) : account.balance == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
