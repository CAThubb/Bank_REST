package com.dimochic.Bank.user.model.dto;

import com.dimochic.Bank.user.model.entity.Role;


public record UserCreateRequestDto(
        String email,
        String username,
        String password,
        Role role
) {
}
