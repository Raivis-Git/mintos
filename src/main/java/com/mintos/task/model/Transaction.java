package com.mintos.task.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue
    public Long id;
    @Column(name = "account_id", nullable = false)
    public Long accountId;

    @Column(name = "updated_balance", nullable = false)
    public BigDecimal updatedBalance;

    @Column(name = "amount", nullable = false)
    public BigDecimal amount;

    @Column(name = "sent", nullable = false)
    public Boolean sent;

    @Column(name = "transfer_account_id", nullable = false)
    public Long transferAccountId;

    @Column(name = "timestamp", nullable = false, updatable = false)
    @CreationTimestamp
    public LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(Long accountId, BigDecimal updatedBalance, BigDecimal amount, Boolean sent, Long transferAccountId) {
        this.accountId = accountId;
        this.updatedBalance = updatedBalance;
        this.amount = amount;
        this.sent = sent;
        this.transferAccountId = transferAccountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(BigDecimal updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Long getTransferAccountId() {
        return transferAccountId;
    }

    public void setTransferAccountId(Long transferAccountId) {
        this.transferAccountId = transferAccountId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
