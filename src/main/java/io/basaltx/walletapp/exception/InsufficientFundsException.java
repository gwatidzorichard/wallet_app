package io.basaltx.walletapp.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException   (String message) {
        super(message);
    }
}
