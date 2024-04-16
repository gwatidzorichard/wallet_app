package io.basaltx.walletapp.service;

import io.basaltx.walletapp.dto.UserDTO;
import io.basaltx.walletapp.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService  {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUserById(UUID userId);

    Optional<User> getUserByUsername(String username);
}
