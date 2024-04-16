package io.basaltx.walletapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Simple Wallet API", version = "0.0.1", description = "RESTful web service that provides functionalities for managing user accounts and transactions within a digital wallet application. Users can create accounts, perform transactions, check balances, and view transaction history."))
public class WalletAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletAppApplication.class, args);
    }

}
