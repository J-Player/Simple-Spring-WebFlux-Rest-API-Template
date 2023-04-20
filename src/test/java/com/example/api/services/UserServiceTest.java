package com.example.api.services;

import com.example.api.domains.User;
import com.example.api.domains.dtos.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.impl.UserService;
import com.example.api.utils.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@ExtendWith(SpringExtension.class)
@DisplayName("User Service Test Class")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();

    private final User user = UserCreator.user();
    private final UserDTO userDTO = UserCreator.userDTO();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.findById(anyLong()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.findAll())
                .thenReturn(Flux.just(user));
        BDDMockito.when(userRepository.save(any(User.class)))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.delete(any(User.class)))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findById | Retorna um User quando bem-sucedido")
    void findById() {
        StepVerifier.create(userService.findById(user.getId()))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("findByUsername | Retorna um Mono Error quando o User nao existir")
    void findById_UserNotFound() {
        BDDMockito.when(userRepository.findById(user.getId()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.findById(user.getId()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("findByName | Retorna um User quando bem-sucedido")
    void findByName() {
        StepVerifier.create(userService.findByName(user.getUsername()))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("findByName | Retorna um Mono Error quando o User nao existir")
    void findByName_UserNotFound() {
        BDDMockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.findByName(user.getUsername()))
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
        StepVerifier.create(userService.save(userDTO))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        BDDMockito.when(userRepository.findById(anyLong()))
                .thenReturn(Mono.just(user));
        StepVerifier.create(userService.update(userDTO, 1L))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("update | Retorna um Mono Error quando o User nao existir")
    void update_UserNotFound() {
        BDDMockito.when(userRepository.findById(user.getId()))
                        .thenReturn(Mono.empty());
        StepVerifier.create(userService.update(userDTO, 1L))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userService.delete(user.getId()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Retorna um Mono Error quando o User nao existir")
    void delete_UserNotFound() {
        BDDMockito.when(userRepository.findById(user.getId()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.delete(user.getId()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

}