package io.basaltx.walletapp.service.impl;

import io.basaltx.walletapp.dto.AccountDto;
import io.basaltx.walletapp.dto.TransactionDTO;
import io.basaltx.walletapp.exception.AccountNotActiveException;
import io.basaltx.walletapp.exception.InsufficientFundsException;
import io.basaltx.walletapp.mapper.AccountMapper;
import io.basaltx.walletapp.mapper.TransactionMapper;
import io.basaltx.walletapp.model.Transaction;
import io.basaltx.walletapp.persistence.TransactionRepository;
import io.basaltx.walletapp.service.AccountService;
import io.basaltx.walletapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public void performTransaction(TransactionDTO transactionDTO) {
        // Retrieve sender and recipient accounts
        AccountDto senderAccount = accountService.getAccountById(transactionDTO.senderAccountId());
        AccountDto recipientAccount = accountService.getAccountById(transactionDTO.recipientAccountId());

        if (!accountService.accountIsActive(senderAccount.getId())){
            throw new AccountNotActiveException("Sender Account is not Active.");
        }

        if (!accountService.accountIsActive(recipientAccount.getId())){
            throw new AccountNotActiveException("Recipient Account is not Active.");
        }

        if (transactionDTO.amount().compareTo(senderAccount.getBalance()) > 0){
            throw new InsufficientFundsException("You have insufficient funds to complete this transaction");
        }
        // Deduct amount from sender account
        senderAccount.setBalance(senderAccount.getBalance().subtract(transactionDTO.amount()));
        // Credit amount to recipient account
        recipientAccount.setBalance(recipientAccount.getBalance().add(transactionDTO.amount()));

        // Save the updated accounts
        AccountDto updatedSenderAcc = accountService.updateAccount(senderAccount);
        AccountDto updatedRecipientAcc = accountService.updateAccount(recipientAccount);

        Transaction transaction = new Transaction();
        transaction.setSenderAccount(accountMapper.toEntity(updatedSenderAcc));
        transaction.setRecipientAccount(accountMapper.toEntity(updatedRecipientAcc));
        transaction.setAmount(transactionDTO.amount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReference(transactionDTO.reference());

        // Save transaction details
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> fetchTransactionHistory(UUID accountId) {
        if (!accountService.accountIsActive(accountId)) {
            throw new AccountNotActiveException("Account is not active");
        } else {
            //TODO sort List by Transaction Date
            List<Transaction> transactionList = transactionRepository.findBySenderAccountIdOrRecipientAccountId(accountId, accountId);
            return transactionList.stream()
                    .map(transactionMapper::toDto)
                    .toList();
        }

    }

}
