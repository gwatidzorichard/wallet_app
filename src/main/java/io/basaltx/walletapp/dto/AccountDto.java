package io.basaltx.walletapp.dto;

import io.basaltx.walletapp.model.enums.AccountStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link io.basaltx.walletapp.model.Account}
 */
@Getter
@Setter
@Builder
public class AccountDto implements Serializable {

    private UUID id;
    private UserDTO user;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

}
