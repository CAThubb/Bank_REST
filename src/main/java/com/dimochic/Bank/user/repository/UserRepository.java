package com.dimochic.Bank.user.repository;

import com.dimochic.Bank.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findUserById(UUID id);

}
