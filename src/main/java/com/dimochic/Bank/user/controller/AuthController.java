package com.dimochic.Bank.user.controller;

import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/0.0.1v/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserResponseDto responseUser = userService.createUser(userCreateRequestDto);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }
}
