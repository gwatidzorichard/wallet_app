package io.basaltx.walletapp.controller;

import io.basaltx.walletapp.dto.LoginRequest;
import io.basaltx.walletapp.dto.LoginResponse;
import io.basaltx.walletapp.exception.WalletErrorResp;
import io.basaltx.walletapp.model.User;
import io.basaltx.walletapp.service.UserService;
import io.basaltx.walletapp.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
            String username = authentication.getName();
            User user = userService.getUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Authentication Failure - Username Not Found: [" + username + "]"));

            String token = jwtUtil.createToken(user);
            LoginResponse loginResponse = LoginResponse.builder().token(token).username(username).build();

            return ResponseEntity.ok(loginResponse);

        }catch (BadCredentialsException e){
            WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Invalid username or password", "api/auth/login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResp);
        }
    }
}
