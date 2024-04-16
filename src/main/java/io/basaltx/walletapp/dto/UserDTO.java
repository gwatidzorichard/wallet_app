package io.basaltx.walletapp.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link io.basaltx.walletapp.model.User}
 */
@Builder
public record UserDTO(UUID id, String username, String firstName, String lastName, String password)implements Serializable {

}
