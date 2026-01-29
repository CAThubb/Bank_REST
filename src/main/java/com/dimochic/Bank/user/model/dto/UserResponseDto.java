package com.dimochic.Bank.user.model.dto;

import com.dimochic.Bank.user.model.entity.Role;
import com.dimochic.Bank.user.model.entity.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String username,
        Role role,
        Status status,
        LocalDateTime createdAt
) {
}
