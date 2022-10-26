package com.example.server.services;

import com.example.server.domains.User;
import com.example.server.repositories.UserRepository;
import com.example.server.utils.UserCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@DisplayName("User Service Test Class")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final User user = UserCreator.createUser();

    @BeforeAll
    public static void blockHound() {
        BlockHound.install();
    }

    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0); //NOSONAR
                return "";
            });
            Schedulers.parallel().schedule(task);
            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.findById(any(UUID.class)))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.findAll())
                .thenReturn(Flux.just(user));
        BDDMockito.when(userRepository.save(user))
                .thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.save(UserCreator.createUser()))
                .thenReturn(Mono.empty());
        BDDMockito.when(userRepository.delete(any(User.class)))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findById | Retorna um User quando bem-sucedido")
    void findById() {
        StepVerifier.create(userService.findById(randomUUID()))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("findByUsername | Retorna um Mono Error quando o User nao existir")
    void findById_UserNotFound() {
        BDDMockito.when(userRepository.findById(any(UUID.class)))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.findById(randomUUID()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("findByName | Retorna um User quando bem-sucedido")
    void findByName() {
        StepVerifier.create(userService.findByName(""))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("findByName | Retorna um Mono Error quando o User nao existir")
    void findByName_UserNotFound() {
        BDDMockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.findByName(""))
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
        StepVerifier.create(userService.save(user))
                .expectSubscription()
                .expectNext(user)
                .expectComplete();
    }

    @Test
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        StepVerifier.create(userService.update(UserCreator.createUser()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("update | Retorna um Mono Error quando o User nao existir")
    void update_UserNotFound() {
        BDDMockito.when(userRepository.findById(any(UUID.class)))
                        .thenReturn(Mono.empty());
        StepVerifier.create(userService.update(UserCreator.createUser()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        StepVerifier.create(userService.delete(randomUUID()))
                .expectSubscription()
                .expectComplete();
    }

    @Test
    @DisplayName("delete | Retorna um Mono Error quando o User nao existir")
    void delete_UserNotFound() {
        BDDMockito.when(userRepository.findById(any(UUID.class)))
                .thenReturn(Mono.empty());
        StepVerifier.create(userService.delete(randomUUID()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

}