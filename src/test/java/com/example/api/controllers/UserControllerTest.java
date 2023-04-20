package com.example.api.controllers;

import com.example.api.controllers.impl.UserController;
import com.example.api.domains.User;
import com.example.api.domains.dtos.UserDTO;
import com.example.api.services.impl.UserService;
import com.example.api.utils.UserCreator;
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

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@DisplayName("User Controller Test Class")
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private final User user = UserCreator.user();
    private final UserDTO userDTO = UserCreator.userDTO();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userService.findById(anyLong()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findByName(anyString()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findAll())
                .thenReturn(Flux.just(user));
        BDDMockito.when(userService.save(any(UserDTO.class)))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.update(any(UserDTO.class), anyLong()))
                .thenReturn(Mono.empty());
        BDDMockito.when(userService.delete(anyLong()))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findById | Retorna um User quando bem-sucedido")
    void findById() {
        StepVerifier.create(userController.findById(user.getId()))
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
        StepVerifier.create(userController.save(userDTO))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        StepVerifier.create(userController.update(userDTO, user.getId()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userController.delete(user.getId()))
                .expectSubscription()
                .expectComplete();
    }

}