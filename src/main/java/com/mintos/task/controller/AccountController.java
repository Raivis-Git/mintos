package com.mintos.task.controller;

import com.mintos.task.controller.model.AccountCreateRequest;
import com.mintos.task.controller.model.TransferFundsRequest;
import com.mintos.task.model.Account;
import com.mintos.task.model.Transaction;
import com.mintos.task.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAccountsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(accountService.fetchAccountsByClientId(clientId));
    }

    @PostMapping()
    public ResponseEntity<?> addAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
        try {
            Account account = accountService.addAccount(accountCreateRequest.iso, accountCreateRequest.balance, accountCreateRequest.clientId);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PostMapping("balance/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferFundsRequest transferFundsRequest) {
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
        List<Transaction> transactionList = accountService.fetchAccountTransactions(accountId, limit, offset);
        return ResponseEntity.ok(transactionList);
    }
}
