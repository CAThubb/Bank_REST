package com.dimochic.Bank.user.service.impl;

import com.dimochic.Bank.exception.BadRequestException;
import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.entity.User;
import com.dimochic.Bank.user.model.mapper.UserMapper;
import com.dimochic.Bank.user.repository.UserRepository;
import com.dimochic.Bank.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {

        if (userRepository.existsByEmail(userCreateRequestDto.email())) {
            throw new BadRequestException("User with this email " + userCreateRequestDto.email() + " already exist");
        }

        if (userRepository.existsByUsername(userCreateRequestDto.username())) {
            throw new BadRequestException("Username is already taken");
        }

        User user = new User.Builder()
                .email(userCreateRequestDto.email())
                .username(userCreateRequestDto.username())
                .password(passwordEncoder.encode(userCreateRequestDto.password()))
                .role(userCreateRequestDto.role())
                .build();

        userRepository.save(user);

        return userMapper.userToResponseDto(user);
    }
}
