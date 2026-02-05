package com.dimochic.Bank.user.service;

import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.dto.jwt.JwtAuthenticationDto;
import com.dimochic.Bank.user.model.dto.jwt.RefreshTokenDto;
import com.dimochic.Bank.user.model.entity.Status;

import java.util.UUID;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto);
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto);
    UserResponseDto updateUserStatus(UUID userId, Status status);
}
