package com.mintos.task.controller;

import com.mintos.task.controller.model.AccountCreateRequest;
import com.mintos.task.controller.model.TransferFundsRequest;
import com.mintos.task.model.Account;
import com.mintos.task.model.Transaction;
import com.mintos.task.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAccountsByClientId(@PathVariable Long clientId) {
        if (clientId == null)
            ResponseEntity.badRequest().build();

        return ResponseEntity.ok(accountService.fetchAccountsByClientId(clientId));
    }

    @PostMapping()
    public ResponseEntity<?> addAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
            if (accountCreateRequest.getIso() == null && accountCreateRequest.getIso().isBlank())
                return ResponseEntity.badRequest().body("Please provide account currency iso");

            if (accountCreateRequest.getBalance() == null)
                return ResponseEntity.badRequest().body("Please provide account balance");

            if (accountCreateRequest.getBalance().compareTo(BigDecimal.ZERO) < 0)
                return ResponseEntity.badRequest().body("Please provide not negative balance");

            if (accountCreateRequest.getClientId() == null)
                return ResponseEntity.badRequest().body("Please provide account client id");

        try {
            Account account = accountService.addAccount(accountCreateRequest.iso, accountCreateRequest.balance, accountCreateRequest.clientId);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PostMapping("balance/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferFundsRequest transferFundsRequest) {
            if (transferFundsRequest.getAmount() == null || transferFundsRequest.getAmount().compareTo(BigDecimal.ZERO) < 0)
                return ResponseEntity.badRequest().body("Amount has to be positive");

            if (transferFundsRequest.getCurrencyIso() == null && transferFundsRequest.getCurrencyIso().isBlank())
                return ResponseEntity.badRequest().body("Please provide currency");

            if (transferFundsRequest.getToAccountId() == null || transferFundsRequest.getFromAccountId() == null)
                return ResponseEntity.badRequest().body("Both accounts for transfer must be provided");

        try {
            List<Account> accountList = accountService.transferFunds(
                    transferFundsRequest.fromAccountId, transferFundsRequest.toAccountId,
                    transferFundsRequest.currencyIso, transferFundsRequest.amount);

            return ResponseEntity.ok(accountList);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @GetMapping(value = "transactions/{accountId}")
    public ResponseEntity<?> getAccountTransactions(@PathVariable Long accountId, @RequestParam Integer limit, @RequestParam Integer offset) {
        if (accountId == null)
            return ResponseEntity.badRequest().body("Please provide account id");

        List<Transaction> transactionList = accountService.fetchAccountTransactions(accountId, limit, offset);
        return ResponseEntity.ok(transactionList);
    }
}
