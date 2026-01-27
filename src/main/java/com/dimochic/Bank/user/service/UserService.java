package com.dimochic.Bank.user.service;

import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto);
}
