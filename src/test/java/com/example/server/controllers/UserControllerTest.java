package com.example.server.controllers;

import com.example.server.domains.User;
import com.example.server.domains.dtos.UserDTO;
import com.example.server.services.UserService;
import com.example.server.utils.UserCreator;
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

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@DisplayName("User Controller Test Class")
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private final User user = UserCreator.createUser();
    private final UserDTO userDTO = UserCreator.createUserDTO();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userService.findById(any(UUID.class)))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findAll())
                .thenReturn(Flux.just(user));
        BDDMockito.when(userService.save(any(User.class)))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.save(any(User.class)))
                .thenReturn(Mono.empty());
        BDDMockito.when(userService.delete(any(UUID.class)))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findById | Retorna um User quando bem-sucedido")
    void findById() {
        StepVerifier.create(userController.findById(randomUUID()))
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
        StepVerifier.create(userController.save(UserCreator.createUserDTO()))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        StepVerifier.create(userController.update(userDTO, randomUUID()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userController.delete(randomUUID()))
                .expectSubscription()
                .expectComplete();
    }

}