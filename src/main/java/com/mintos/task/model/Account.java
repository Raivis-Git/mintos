package com.mintos.task.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name ="client_id", nullable = false)
    private Client client;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) throws Exception {
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new Exception("Balance must be positive");
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCurrencyIso() {
        return getCurrency().getIso();
    }

    public Long getId() {
        return id;
    }
}
