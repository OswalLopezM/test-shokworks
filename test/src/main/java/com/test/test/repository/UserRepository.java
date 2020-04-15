package com.test.test.repository;

import com.test.test.model.Role;
import com.test.test.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);

    Boolean existsByEmail(String email);
}
