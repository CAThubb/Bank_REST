package com.dimochic.Bank.user.controller;

import com.dimochic.Bank.exception.BadRequestException;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.entity.User;
import com.dimochic.Bank.user.model.mapper.UserMapper;
import com.dimochic.Bank.user.repository.UserRepository;
import com.dimochic.Bank.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/0.2v/users")
@SecurityRequirement(name = "Authentication")
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@Tag(name = "User: User operations", description = "Endpoints for users to manage their profile")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserController(UserService userService, UserRepository userRepository, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserResponseDto> getUserCredential() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User getUserData = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        UserResponseDto response = userMapper.userToResponseDto(getUserData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
