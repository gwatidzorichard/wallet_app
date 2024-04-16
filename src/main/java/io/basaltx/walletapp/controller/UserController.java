package io.basaltx.walletapp.controller;

import io.basaltx.walletapp.dto.UserDTO;
import io.basaltx.walletapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.registerUser(userDTO);
        return ResponseEntity.ok(user);
    }

  /*  @PostMapping("/login")
    public ResponseEntity<String> loginUser(String username, String password) {
        String token = userService.loginUser(username, password);
        return ResponseEntity.ok(new AuthResponse(token));
    }*/

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable UUID userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }
}
