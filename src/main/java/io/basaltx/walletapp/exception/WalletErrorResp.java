package io.basaltx.walletapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletErrorResp {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private Map<String, String> errors = new HashMap<>();

    public WalletErrorResp(int status, String message, String error, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors.put("error", error);
    }

    public WalletErrorResp(int status, String message, Map<String, String> errors, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

}
