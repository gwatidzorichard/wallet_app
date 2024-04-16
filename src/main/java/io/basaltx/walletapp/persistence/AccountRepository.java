package io.basaltx.walletapp.persistence;

import io.basaltx.walletapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByUserId(UUID accountId);
}
