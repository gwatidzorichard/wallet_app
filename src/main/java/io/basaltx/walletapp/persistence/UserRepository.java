package io.basaltx.walletapp.persistence;

import io.basaltx.walletapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
