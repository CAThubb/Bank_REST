package com.dimochic.Bank.security;

import com.dimochic.Bank.user.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    private final String jwtSecret = "z3X09EF35GsrnomLPPk07T1ezSXjpaGVcPeUT5jfvoG";
    private final long accessExpMs = 3600000; // 10 minutes
    private final long refreshExpMs = 604800000; // 7 days

    @Autowired
    private JwtService jwtService;

    @Test
    void generateAccessToken_givenValidUserDetail_shouldReturnSuccess() {

        JwtService jwtService = new JwtService(
                jwtSecret,
                accessExpMs,
                refreshExpMs
        );

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User("test@mail.com", "password", authorities);

        String token = jwtService.generateAccessToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtService.validateToken(token));


        String extractEmail = jwtService.extractEmail(token);
        assertEquals("test@mail.com", extractEmail);
        assertTrue(jwtService.isTokenEmailValid(token, userDetails));

        List<String> roles = jwtService.extractRoles(token);
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("USER", roles.get(0));


    }

    @Test
    void generateRefreshToken_givenValidUserDetail_shouldReturnSuccess() {

        JwtService jwtService = new JwtService(
                jwtSecret,
                accessExpMs,
                refreshExpMs
        );

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User("test@mail.com", "password", authorities);


        String token = jwtService.generateRefreshToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtService.validateToken(token));


        String extractEmail = jwtService.extractEmail(token);
        assertEquals("test@mail.com", extractEmail);
        assertTrue(jwtService.isTokenEmailValid(token, userDetails));

        List<String> roles = jwtService.extractRoles(token);
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("USER", roles.get(0));
    }

    @Test
    void validateToken_givenInvalidToken_shouldReturnFalse() {
        String invalidToken = "some.invalid.token";

        assertFalse(jwtService.validateToken(invalidToken));
    }

    @Test
    void validateToken_givenExpiredToken_shouldReturnFalse() {
        JwtService jwtService = new JwtService(
                jwtSecret,
                1L,  // 1 millisecond
                refreshExpMs
        );

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User("test@mail.com", "password", authorities);

        String shortToken = jwtService.generateAccessToken(userDetails);
        assertFalse(jwtService.validateToken(shortToken));
    }

    @Test
    void isTokenEmailValid_givenWrongEmail_shouldReturnFalse() {
        JwtService jwtService = new JwtService(
                jwtSecret,
                accessExpMs,
                refreshExpMs
        );

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User("test1@mail.com", "password", authorities);
        UserDetails userDetailsInvalid = new User("test2@mail.com", "password", authorities);

        String token = jwtService.generateAccessToken(userDetails);
        assertNotNull(token);
        assertFalse(jwtService.isTokenEmailValid(token, userDetailsInvalid));
    }

    @Test
    void validateToken_givenInvalidSignature_shouldReturnFalse() {
        JwtService jwtService = new JwtService(
                jwtSecret,
                accessExpMs,
                refreshExpMs
        );
        UserDetails userDetails = new User("test1@mail.com", "password", List.of());
        String token = jwtService.generateAccessToken(userDetails);

        String[] parts = token.split("\\.");
        String invalidToken = parts[0] + "." + parts[1] + ".invalidTokenSignature";

        assertFalse(jwtService.validateToken(invalidToken));
    }
}
