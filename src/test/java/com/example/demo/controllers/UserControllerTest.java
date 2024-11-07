package com.example.demo.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controllers.impl.UserController;
import com.example.demo.models.entities.User;
import com.example.demo.models.security.RegisterRequest;
import com.example.demo.services.impl.UserService;
import com.example.demo.util.UserCreator;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DisplayName("User Controller Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private final User user = UserCreator.user();
    private final RegisterRequest registerRequest = UserCreator.registerRequest();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userService.findById(anyInt()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findByName(anyString()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findAll(Pageable.unpaged()))
                .thenReturn(Mono.just(new PageImpl<>(List.of(user))));
        BDDMockito.when(userService.save(any(User.class)))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.update(any(User.class)))
                .thenReturn(Mono.empty());
        BDDMockito.when(userService.delete(anyInt()))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findByName | Returns a user when successful")
    void findByName() {
        StepVerifier.create(userController.findByName(""))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById | Returns a user when successful")
    void findById() {
        StepVerifier.create(userController.findById(123))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("listAll | Returns all users when successful")
    void listAll() {
        StepVerifier.create(userController.listAll(Pageable.unpaged()))
                .expectSubscription()
                .expectNext(new PageImpl<>(List.of(user)))
                .verifyComplete();
    }

    @Test
    @DisplayName("save | Returns a user when successful")
    void save() {
        BDDMockito.when(userService.findByName(anyString()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userController.save(registerRequest))
                .expectSubscription()
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("update | Returns status 204 (no content) when successful")
    void update() {
        StepVerifier.create(userController.update(123, registerRequest))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("delete | Returns status 204 (no content) when successful")
    void delete() {
        StepVerifier.create(userController.delete(123))
                .expectSubscription()
                .verifyComplete();
    }

}
