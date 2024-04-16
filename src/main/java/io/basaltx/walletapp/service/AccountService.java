package io.basaltx.walletapp.service;

import io.basaltx.walletapp.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(AccountDto userAccount);

    AccountDto getAccountById(UUID accountId);

    List<AccountDto> getAccounts();

    BigDecimal getAccountBalance(UUID accountId);
    boolean accountIsActive(UUID accountId);
    boolean accountExists(UUID accountId);

    boolean accountExistsByUser(UUID accountId);
}
