package io.basaltx.walletapp.service.impl;

import io.basaltx.walletapp.dto.AccountDto;
import io.basaltx.walletapp.dto.UserDTO;
import io.basaltx.walletapp.exception.AccountCreationException;
import io.basaltx.walletapp.exception.AccountNotActiveException;
import io.basaltx.walletapp.exception.AccountUpdateException;
import io.basaltx.walletapp.exception.ResourceNotFoundException;
import io.basaltx.walletapp.mapper.AccountMapper;
import io.basaltx.walletapp.mapper.UserMapper;
import io.basaltx.walletapp.model.Account;
import io.basaltx.walletapp.model.enums.AccountStatus;
import io.basaltx.walletapp.persistence.AccountRepository;
import io.basaltx.walletapp.service.AccountService;
import io.basaltx.walletapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserService userService;
    private final UserMapper userMapper;


    @Override
    public AccountDto createAccount(AccountDto newAccountDto) {

        if (Objects.nonNull(newAccountDto)){

            if (accountExistsByUser(newAccountDto.getUser().id())) {
                throw new AccountCreationException(String.format("Account for UserId %s already exists", newAccountDto.getUser().id()));
            }
            UserDTO user = userService.getUserById(newAccountDto.getUser().id());

            Account newAccount = accountMapper.toEntity(newAccountDto);
            newAccount.setUser(userMapper.toEntity(user));
            return accountMapper.toDto(accountRepository.save(newAccount));

        } else {
            throw new AccountCreationException("Account Details cannot be null");
        }
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        if (Objects.nonNull(accountDto)){
            if(accountExists(accountDto.getId())){
                accountRepository.save(accountMapper.toEntity(accountDto));
                return accountDto;
            } else throw new AccountUpdateException(String.format("Account with accountId %s cannot be found", accountDto.getId()));

        } else throw new AccountUpdateException("Account request cannot be null");

    }

    @Override
    public AccountDto getAccountById(UUID accountId) {

         Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Find Account", "Account", "ID", accountId));
         return accountMapper.toDto(account);
    }
    @Override
    public List<AccountDto> getAccounts() {

        List<Account> accountList = accountRepository.findAll();

        return accountList.stream().map(accountMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getAccountBalance(UUID accountId) {

        if(accountExists(accountId)){
            AccountDto account = getAccountById(accountId);
            if (accountIsActive(accountId)){
                return account.getBalance();
            } else {
                throw new AccountNotActiveException("Account is not Active. Account Status is "+ account.getStatus());
            }
        } else {
            throw new ResourceNotFoundException("Find Account", "Account", "ID", accountId);
        }

    }
    @Override
    public boolean accountIsActive(UUID accountId) {
        if(accountExists(accountId)){
            AccountDto existingAccountDto = getAccountById(accountId);
            return existingAccountDto.getStatus().equals(AccountStatus.ACTIVE);
        } else return false;
    }

    @Override
    public boolean accountExists(UUID accountId) {
        return accountRepository.existsById(accountId);
    }

    @Override
    public boolean accountExistsByUser(UUID accountId) {
        return accountRepository.existsByUserId(accountId);
    }

}
