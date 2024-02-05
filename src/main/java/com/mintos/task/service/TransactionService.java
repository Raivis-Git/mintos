package com.mintos.task.service;

import com.mintos.task.model.Account;
import com.mintos.task.model.Transaction;
import com.mintos.task.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void createTransaction(Account fromAccount, Account toAccount, BigDecimal amount, boolean save) {
        Transaction senderTransaction = new Transaction(fromAccount.getId(), fromAccount.getBalance(), amount, true, toAccount.getId());
        Transaction receiverTransaction = new Transaction(toAccount.getId(), toAccount.getBalance(), amount, false, fromAccount.getId());
        if (!save)
            return;

        saveTransaction(senderTransaction);
        saveTransaction(receiverTransaction);
    }

    public List<Transaction> fetchTransactionsByAccountId(Long accountId, Pageable pageable) {
        return transactionRepository.findByAccountId(accountId, pageable);
    }

}
