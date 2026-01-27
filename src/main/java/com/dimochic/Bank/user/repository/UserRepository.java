package com.dimochic.Bank.user.repository;

import com.dimochic.Bank.user.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
