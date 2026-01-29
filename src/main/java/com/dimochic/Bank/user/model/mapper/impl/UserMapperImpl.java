package com.dimochic.Bank.user.model.mapper.impl;

import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.entity.User;
import com.dimochic.Bank.user.model.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponseDto userToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}
