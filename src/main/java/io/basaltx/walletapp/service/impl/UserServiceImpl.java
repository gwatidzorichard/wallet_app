package io.basaltx.walletapp.service.impl;

import io.basaltx.walletapp.dto.UserDTO;
import io.basaltx.walletapp.exception.UserAlreadyExistsException;
import io.basaltx.walletapp.exception.UserNotFoundException;
import io.basaltx.walletapp.mapper.UserMapper;
import io.basaltx.walletapp.model.User;
import io.basaltx.walletapp.persistence.UserRepository;
import io.basaltx.walletapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        if (userExistsByUsername(userDTO.username())) {
            throw new UserAlreadyExistsException(String.format("User with username %s already exists", userDTO.username()));
        } else {
            String encryptedPassword = encryptPassword(userDTO.password());

            User userEntity = userMapper.toEntity(userDTO);
            userEntity.setPassword(encryptedPassword);
            userEntity.setId(UUID.randomUUID());

            return userMapper.toDto(userRepository.save(userEntity));
        }
    }
    @Override
    public UserDTO getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' not found", userId)));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean userExists(UUID userId  ){
        return userRepository.existsById(userId);
    }

    public boolean userExistsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    private String encryptPassword(String rawPassword){

        PasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        return bcryptPasswordEncoder.encode(rawPassword);
    }
}
