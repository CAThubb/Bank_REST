package com.dimochic.Bank.user.controller;

import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.dto.jwt.JwtAuthenticationDto;
import com.dimochic.Bank.user.model.dto.jwt.UserCredentialsDto;
import com.dimochic.Bank.user.security.JwtService;
import com.dimochic.Bank.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/0.0.1v/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserResponseDto responseUser = userService.createUser(userCreateRequestDto);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredentialsDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return ResponseEntity.ok(new JwtAuthenticationDto(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}
