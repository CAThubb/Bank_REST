package com.dimochic.Bank.user.model.dto.jwt;

public record UserCredentialsDto(
        String email,
        String password
) {
}
