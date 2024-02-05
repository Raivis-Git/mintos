package com.mintos.task.controller.model;

import java.math.BigDecimal;

public class AccountCreateRequest {
    public String iso;
    public BigDecimal balance;
    public Long clientId;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
