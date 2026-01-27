package com.dimochic.Bank.UserTest;

import com.dimochic.Bank.exception.BadRequestException;
import com.dimochic.Bank.user.model.dto.UserCreateRequestDto;
import com.dimochic.Bank.user.model.dto.UserResponseDto;
import com.dimochic.Bank.user.model.entity.Role;
import com.dimochic.Bank.user.model.entity.Status;
import com.dimochic.Bank.user.repository.UserRepository;
import com.dimochic.Bank.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Container
    static PostgreSQLContainer<?> posgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("banktest")
            .withUsername("testuser")
            .withPassword("testpassword");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", posgres::getJdbcUrl);
        registry.add("spring.datasource.username", posgres::getUsername);
        registry.add("spring.datasource.password", posgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUserSuccess() {
        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(
                "newuser@mail.com",
                "kinki",
                "1234567",
                Role.USER
        );
        UserResponseDto newUser = userService.createUser(userCreateRequestDto);
        assertThat(newUser.id()).isNotNull();
        assertThat(newUser.email()).isEqualTo(userCreateRequestDto.email());
        assertThat(newUser.username()).isEqualTo(userCreateRequestDto.username());
        assertThat(newUser.role()).isEqualTo(Role.USER);
        assertThat(newUser.status()).isEqualTo(Status.ENABLED);
    }

    @Test
    void createUserWithMailExistsThrowsBadRequestException() {
        UserCreateRequestDto existingUser = new UserCreateRequestDto(
                "existing@mail.com",
                "ImHere",
                "12345678",
                Role.USER
        );

        userService.createUser(existingUser);

        UserCreateRequestDto duplicatedEmail = new UserCreateRequestDto(
                "existing@mail.com",
                "mike",
                "newpass1234",
                Role.USER
        );
        assertThatThrownBy(() -> userService.createUser(duplicatedEmail))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void createUserWithUsernameExistsThrowsBadRequestException() {
        UserCreateRequestDto existingUser = new UserCreateRequestDto(
                "existing@mail.com",
                "ImHere",
                "12345678",
                Role.USER
        );

        userService.createUser(existingUser);

        UserCreateRequestDto duplicatedUsername = new UserCreateRequestDto(
                "existing@mail.com",
                "mike",
                "newpass1234",
                Role.USER
        );
        assertThatThrownBy(() -> userService.createUser(duplicatedUsername))
                .isInstanceOf(BadRequestException.class);
    }
}
