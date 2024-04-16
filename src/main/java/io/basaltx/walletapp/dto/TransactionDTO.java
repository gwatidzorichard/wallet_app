package io.basaltx.walletapp.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link io.basaltx.walletapp.model.Transaction}
 */
@Builder
public record TransactionDTO(UUID senderAccountId, UUID recipientAccountId, BigDecimal amount, String reference) {

}
