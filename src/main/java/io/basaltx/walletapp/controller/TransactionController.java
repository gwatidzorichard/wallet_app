package io.basaltx.walletapp.controller;

import io.basaltx.walletapp.dto.TransactionDTO;
import io.basaltx.walletapp.mapper.TransactionMapper;
import io.basaltx.walletapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/perform")
    public ResponseEntity<String> performTransaction(@RequestBody TransactionDTO transactionRequest) {
        transactionService.performTransaction(transactionRequest);
        return ResponseEntity.ok("Transaction performed successfully");
    }

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable UUID accountId) {
        List<TransactionDTO> transactions = transactionService.fetchTransactionHistory(accountId);
        return ResponseEntity.ok(new ArrayList<>(transactions));
    }
}
