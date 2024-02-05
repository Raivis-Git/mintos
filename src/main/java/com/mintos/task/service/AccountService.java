package com.mintos.task.service;

import com.mintos.task.model.Account;
import com.mintos.task.model.Client;
import com.mintos.task.model.Currency;
import com.mintos.task.model.Transaction;
import com.mintos.task.repository.AccountRepository;
import com.mintos.task.repository.pageable.OffsetLimitPageable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    public List<Account> transferFunds(Long fromAccountId, Long toAccountId, String iso, BigDecimal amount) throws Exception {

        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(()-> new Exception("Account with id:" + fromAccountId + " not found"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(()-> new Exception("Account with id:" + toAccountId + " not found"));
        iso = iso.toUpperCase();

        if (!toAccount.getCurrencyIso().equals(iso.toUpperCase()))
            throw new Exception("Given currency does not match the currency of the receiving account");

        BigDecimal fromBalance = fromAccount.getBalance();
        BigDecimal toBalance = toAccount.getBalance();

        if (fromAccount.getCurrencyIso().equals(iso.toUpperCase())) {
            fromAccount.setBalance(fromBalance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN));
            toAccount.setBalance(toBalance.add(amount).setScale(2, RoundingMode.HALF_EVEN));
        } else {
            currencyService.fetchCurrencyByIso(iso);
            BigDecimal convertedAmount = currencyService.convert(amount,fromAccount.getCurrencyIso(), toAccount.getCurrencyIso());
            fromAccount.setBalance(fromBalance.subtract(convertedAmount).setScale(2, RoundingMode.HALF_EVEN));
            toAccount.setBalance(toBalance.add(convertedAmount).setScale(2, RoundingMode.HALF_EVEN));
        }

        transactionService.createTransaction(fromAccount, toAccount, amount, true);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return Arrays.asList(fromAccount, toAccount);
    }

    @Transactional
    public Account addAccount(String iso, BigDecimal balance, Long clientId) throws Exception {
        Account account = new Account();
        Currency currency = currencyService.fetchCurrencyByIso(iso).orElseThrow(()-> new Exception("Unsupported currency"));
        Client client = clientService.fetchClientById(clientId).orElseThrow(()-> new Exception("Client not found"));

        account.setCurrency(currency);
        account.setBalance(balance);
        account.setClient(client);

        return accountRepository.save(account);
    }

    public List<Transaction> fetchAccountTransactions(Long accountId, Integer limit, Integer offset) {
        Pageable pageable = new OffsetLimitPageable(offset, limit,
                Sort.by(Sort.Order.desc("timestamp")));
        return transactionService.fetchTransactionsByAccountId(accountId, pageable);
    }

    public List<Account> fetchAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
