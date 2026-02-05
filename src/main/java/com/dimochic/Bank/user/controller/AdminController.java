package com.dimochic.Bank.user.controller;

import com.dimochic.Bank.exception.UserNotFoundException;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.entity.Status;
import com.dimochic.Bank.user.model.entity.User;
import com.dimochic.Bank.user.model.mapper.UserMapper;
import com.dimochic.Bank.user.repository.UserRepository;
import com.dimochic.Bank.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/0.2v/admin")
@SecurityRequirement(name = "Authentication")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin: Admin operations", description = "Endpoints for admin to manage users and their data")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AdminController(UserService userService, UserRepository userRepository, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserResponseDto> getUserGyId(
            @PathVariable("id") UUID userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id=" + userId + " not found"));
        UserResponseDto response = userMapper.userToResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(path = "/users/{id}/disable")
    public ResponseEntity<UserResponseDto> disableUser(
            @PathVariable("id") UUID userId) {
        UserResponseDto userResponse = userService.updateUserStatus(userId, Status.DISABLED);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PatchMapping(path = "/users/{id}/enable")
    public ResponseEntity<UserResponseDto> enableUser(
            @PathVariable("id") UUID userId) {
        UserResponseDto userResponse = userService.updateUserStatus(userId, Status.ENABLED);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
