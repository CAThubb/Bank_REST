package com.dimochic.Bank.user.model.mapper;

import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.entity.User;

public interface UserMapper {
    UserResponseDto userToResponseDto(User user);
}
