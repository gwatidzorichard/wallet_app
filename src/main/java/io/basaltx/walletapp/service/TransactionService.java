package io.basaltx.walletapp.service;

import io.basaltx.walletapp.dto.TransactionDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    @Transactional
    void performTransaction(TransactionDTO transactionRequest);

    List<TransactionDTO> fetchTransactionHistory(UUID accountId);
}
