package com.server.controllers;

import com.server.domains.User;
import com.server.services.UserService;
import com.server.utils.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@DisplayName("User Controller Test Class")
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private final User user = UserCreator.createValidUser();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userService.findById(anyInt()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findAll())
                .thenReturn(Flux.just(user));
        BDDMockito.when(userService.save(UserCreator.createToSaved()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.save(UserCreator.createValidUser()))
                .thenReturn(Mono.empty());
        BDDMockito.when(userService.delete(anyInt()))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findByUsername | Retorna um User quando bem-sucedido")
    void findById() {
        StepVerifier.create(userController.findById(1))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("findAll | Retorna um Flux de User quando bem-sucedido")
    void findAll() {
        StepVerifier.create(userController.findAll())
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("save | Salva um User no banco de dados quando bem-sucedido")
    void save() {
        StepVerifier.create(userController.save(UserCreator.createToSaved()))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        StepVerifier.create(userController.update(1, UserCreator.createValidUser()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userController.delete(1))
                .expectSubscription()
                .expectComplete();
    }

}