package com.dimochic.Bank.user.model.dto.jwt;

public record JwtAuthenticationDto(
        String token,
        String refreshToken
) {
}
