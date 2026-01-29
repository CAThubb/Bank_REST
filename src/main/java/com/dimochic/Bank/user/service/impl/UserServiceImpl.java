package com.dimochic.Bank.user.service.impl;

import com.dimochic.Bank.exception.BadRequestException;
import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.dto.jwt.JwtAuthenticationDto;
import com.dimochic.Bank.user.model.dto.jwt.RefreshTokenDto;
import com.dimochic.Bank.user.model.entity.User;
import com.dimochic.Bank.user.model.mapper.UserMapper;
import com.dimochic.Bank.user.repository.UserRepository;
import com.dimochic.Bank.user.security.JwtService;
import com.dimochic.Bank.user.service.UserService;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
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

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String token = refreshTokenDto.refreshToken();
        if (!jwtService.validateToken(token)) {
            throw new MalformedJwtException("Invalid JWT token");
        }

        String tokenType = jwtService.extractTokenType(token);
        if (!tokenType.equals("refresh")) {
            throw new IllegalArgumentException("Invalid JWT token type: not a refresh token");
        }

        String email = jwtService.extractEmail(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return new JwtAuthenticationDto(newAccessToken, newRefreshToken);
    }
}
