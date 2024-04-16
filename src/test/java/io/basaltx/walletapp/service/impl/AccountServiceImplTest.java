
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
import io.basaltx.walletapp.model.User;
import io.basaltx.walletapp.model.enums.AccountStatus;
import io.basaltx.walletapp.persistence.AccountRepository;
import io.basaltx.walletapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AccountServiceImpl accountService;
    private AccountDto newAccountDto;
    private AccountDto updatedAccountDto;
    private AccountDto existingAccountDto;
    private AccountDto nonExistingAccountDto;
    private AccountDto inactiveAccountDto;

    @BeforeEach
    void setUp() {
        newAccountDto = AccountDto.builder()
                .user(getUserDto(UUID.randomUUID()))
                .id(UUID.randomUUID())
                .balance(BigDecimal.TEN)
                .status(AccountStatus.ACTIVE)
                .currency("ZAR")
                .build();


        updatedAccountDto = AccountDto.builder()
                .currency("ZAR")
                .user(getUserDto(UUID.randomUUID()))
                .balance(BigDecimal.valueOf(10000.00))
                .status(AccountStatus.ACTIVE)
                .id(UUID.randomUUID())
                .build();


        existingAccountDto = AccountDto.builder()
                .user(getUserDto(UUID.randomUUID()))
                .id(UUID.randomUUID())
                .balance(BigDecimal.TEN)
                .status(AccountStatus.ACTIVE)
                .currency("ZAR")
                .build();


        nonExistingAccountDto = AccountDto.builder()
                .user(getUserDto(UUID.randomUUID()))
                .id(UUID.randomUUID())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .build();


        inactiveAccountDto = AccountDto.builder()
                .status(AccountStatus.INACTIVE)
                .balance(BigDecimal.TEN)
                .user(getUserDto(UUID.randomUUID()))
                .id(UUID.randomUUID())
                .build();
    }

    @Test
    void createAccount_whenAccountExistsByUser_throwsException() {
        UUID accountId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        when(accountService.accountExistsByUser(userId)).thenReturn(true);

        assertThrows(AccountCreationException.class, () -> accountService.createAccount(getAccountDto(accountId, userId)));
    }

    @Test
    void testCreateAccount_Success() {
        User user = getUser(UUID.randomUUID());
        Account account = getAccount(UUID.randomUUID(), user.getId());
        AccountDto accountDto = getAccountDto(user.getId(), account.getId());

        when(userService.getUserById(any(UUID.class))).thenReturn(getUserDto(user.getId()));
        when(accountMapper.toEntity(accountDto)).thenReturn(account);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(accountDto);

        assertNotNull(result);
        verify(accountRepository, times(1)).save(any(Account.class));
    }


    @Test
    void updateAccount_whenAccountExists_updatesAccount() {
        when(accountRepository.existsById(updatedAccountDto.getId())).thenReturn(true);
        updatedAccountDto.setBalance(BigDecimal.valueOf(23500.00));

        AccountDto updatedAccountDto1 = accountService.updateAccount(updatedAccountDto);

        assertEquals(updatedAccountDto1.getId(), updatedAccountDto.getId());
        assertEquals(updatedAccountDto1.getUser().id(), updatedAccountDto.getUser().id());
        assertEquals(updatedAccountDto1.getBalance(), BigDecimal.valueOf(23500.00));
        assertEquals(updatedAccountDto1.getStatus(), updatedAccountDto.getStatus());
    }

    @Test
    void updateAccount_whenAccountDoesNotExist_throwsException() {
        when(accountRepository.existsById(any())).thenReturn(false);

        assertThrows(AccountUpdateException.class, () -> accountService.updateAccount(updatedAccountDto));
    }

    @Test
    void getAccountById_whenAccountExists_returnsAccount() {
        when(accountRepository.findById(existingAccountDto.getId())).thenReturn(Optional.of(getAccount(existingAccountDto.getId(), existingAccountDto.getUser().id())));
        when(accountMapper.toDto(any(Account.class))).thenReturn(existingAccountDto);

        AccountDto returnedAccountDto = accountService.getAccountById(existingAccountDto.getId());

        assertEquals(existingAccountDto.getId(), returnedAccountDto.getId());
        assertEquals(existingAccountDto.getUser().id(), returnedAccountDto.getUser().id());
        assertEquals(existingAccountDto.getBalance(), returnedAccountDto.getBalance());
        assertEquals(existingAccountDto.getStatus(), returnedAccountDto.getStatus());
    }

    @Test
    void getAccountById_whenAccountDoesNotExist_throwsException() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountById(existingAccountDto.getId()));
    }

    @Test
    void getAccountBalance_whenAccountExistsAndIsActive_returnsBalance() {
        UUID accountId = existingAccountDto.getId();
        UUID userId = existingAccountDto.getUser().id();

        when(accountRepository.existsById(accountId)).thenReturn(true);
        when(accountRepository.findById(any())).thenReturn(Optional.of(getAccount(accountId, userId)));
        when(accountService.getAccountById(accountId)).thenReturn(existingAccountDto);

        BigDecimal returnedBalance = accountService.getAccountBalance(accountId);

        assertEquals(existingAccountDto.getBalance(), returnedBalance);
    }

    @Test
    void getAccountBalance_whenAccountExistsButIsNotActive_throwsException() {
        UUID accountId = inactiveAccountDto.getId();
        UUID userId = inactiveAccountDto.getUser().id();

        when(accountRepository.existsById(accountId)).thenReturn(true);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(getAccount(accountId, userId)));
        when(accountService.getAccountById(accountId)).thenReturn(inactiveAccountDto);

        assertThrows(AccountNotActiveException.class, () -> accountService.getAccountBalance(accountId));
    }

    @Test
    void getAccounts_whenAccountsExist_thenListOfAccountsReturned() {
        // Given
        List<Account> accounts = Collections.singletonList(new Account());
        lenient().when(accountRepository.findAll()).thenReturn(accounts);

        // When
        List<AccountDto> accountDtos = accountService.getAccounts();

        // Then
        assertNotNull(accountDtos);
        assertFalse(accountDtos.isEmpty());
    }

    @Test
    void getAccountBalance_whenAccountExistsAndIsActive_thenBalanceReturned() {
        // Given
        UUID accountId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        BigDecimal balance = BigDecimal.valueOf(100.00);
        AccountDto accountDto = getAccountDto(accountId, userId);

        // When
        when(accountService.accountExists(accountId)).thenReturn(true);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(getAccount(accountId, userId)));
        when(accountService.getAccountById(accountId)).thenReturn(accountDto);

        BigDecimal retrievedBalance = accountService.getAccountBalance(accountId);

        // Then
        assertEquals(balance, retrievedBalance);
    }


    @Test
    void getAccountBalance_whenAccountDoesNotExist_throwsException() {
        lenient().when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountBalance(existingAccountDto.getId()));
    }

    @Test
    void accountIsActive_whenAccountExists_returnsTrue() {
        when(accountService.accountExists(any())).thenReturn(true);
        assertEquals(true, accountService.accountExists(existingAccountDto.getId()));
    }

    private UserDTO getUserDto(UUID userId) {
        return new UserDTO(userId, "johndoe", "John", "Doe", "password");
    }

    private User getUser(UUID userId) {
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        return user;
    }

    private AccountDto getAccountDto(UUID accountId, UUID userId) {
        return AccountDto.builder()
                .id(accountId)
                .balance(BigDecimal.valueOf(100.00))
                .user(getUserDto(userId))
                .status(AccountStatus.ACTIVE)
                .currency("ZAR")
                .build();
    }

    private Account getAccount(UUID accountId, UUID userId) {
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(100.00));
        account.setUser(getUser(userId));
        account.setStatus(AccountStatus.ACTIVE);
        account.setCurrency("ZAR");
        return account;
    }
}

