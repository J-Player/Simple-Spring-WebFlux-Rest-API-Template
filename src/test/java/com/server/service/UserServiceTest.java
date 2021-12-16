package com.server.service;

import com.server.domain.User;
import com.server.repository.UserRepository;
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
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DisplayName("User Service Test Class")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final User user = UserCreator.createValidUser();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(user));

        BDDMockito.when(userRepository.findAll())
                .thenReturn(Flux.just(user));

        //SAVE
        BDDMockito.when(userRepository.save(UserCreator.createToSaved()))
                .thenReturn(Mono.just(user));

        //UPDATE
        BDDMockito.when(userRepository.save(UserCreator.createValidUser()))
                .thenReturn(Mono.empty());

        //DELETE
        BDDMockito.when(userRepository.delete(ArgumentMatchers.any(User.class)))
                .thenReturn(Mono.empty());

    }

    @Test
    @DisplayName("findById | Retorna um User quando bem-sucedido")
    void findById() {
        StepVerifier.create(userService.findById(1))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("findById | Retorna um Mono Error quando o User não existir")
    void findById_UserNotFound() {
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.findById(1))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("findAll | Retorna um Flux de User quando bem-sucedido")
    void findAll() {
        StepVerifier.create(userService.findAll())
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("save | Salva um User no banco de dados quando bem-sucedido")
    void save() {
        StepVerifier.create(userService.save(UserCreator.createToSaved()))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        StepVerifier.create(userService.update(UserCreator.createValidUser()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("update | Retorna um Mono Error quando o User não existir")
    void update_UserNotFound() {
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyInt()))
                        .thenReturn(Mono.empty());
        StepVerifier.create(userService.update(UserCreator.createValidUser()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userService.delete(user))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Retorna um Mono Error quando o User não existir")
    void delete_UserNotFound() {
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.delete(user))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

}