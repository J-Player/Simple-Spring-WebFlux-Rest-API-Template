package com.server.controller;

import com.server.domain.User;
import com.server.service.UserService;
import com.server.util.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        BDDMockito.when(userService.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userService.findAll())
                .thenReturn(Flux.just(user));

        //SAVE
        BDDMockito.when(userService.save(UserCreator.createToSaved()))
                .thenReturn(Mono.just(user));

        //UPDATE
        BDDMockito.when(userService.save(UserCreator.createValidUser()))
                .thenReturn(Mono.empty());

        //DELETE
        BDDMockito.when(userService.delete(ArgumentMatchers.any(User.class)))
                .thenReturn(Mono.empty());
    }


    @Test
    @DisplayName("findById | Retorna um User quando bem-sucedido")
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
        StepVerifier.create(userController.update(UserCreator.createValidUser()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userController.delete(user))
                .expectSubscription()
                .expectComplete();
    }

}