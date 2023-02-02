package com.example.server.integration;

import com.example.server.domains.User;
import com.example.server.domains.dtos.UserDTO;
import com.example.server.repositories.UserRepository;
import com.example.server.services.UserService;
import com.example.server.utils.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DisplayName("User Controller Integration")
class UserControllerIT {

    private static final String ADMIN_USER = "admin";
    private static final String PATH = "/users";

    @Autowired
    private UserService userService;

    @Autowired
    private WebTestClient client;

    @MockBean
    private UserRepository userRepository;

    private final User user = UserCreator.createUser();
    private final UserDTO userDTO = UserCreator.createUserDTO();

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.findById(any(UUID.class))).thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.findAll(any(Sort.class))).thenReturn(Flux.just(user));
        BDDMockito.when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        BDDMockito.when(userRepository.delete(any(User.class))).thenReturn(Mono.empty());
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("findById | Retorna um User quando bem-sucedido")
    void findById() {
        client.get()
                .uri(PATH.concat("/{id}"), user.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("findByUsername | Retorna um User quando bem-sucedido")
    void findByUsername() {
        client.get()
                .uri(builder -> builder
                        .path(PATH)
                        .queryParam("username", user.getUsername())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("findAll | Retorna um Flux de User quando bem-sucedido")
    void findAll() {
        client.get()
                .uri(builder -> builder.path(PATH.concat("/all")).build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(1)
                .contains(user);
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("save | Salva um User no banco de dados quando bem-sucedido")
    void save() {
        client.post()
                .uri(PATH)
                .bodyValue(userDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("update | Atualiza um User no banco de dados quando bem-sucedido")
    void update() {
        client.put()
                .uri(PATH.concat("/{id}"), user.getId())
                .bodyValue(userDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("delete | Exclui um User do banco de dados quando bem-sucedido")
    void delete() {
        client.delete()
                .uri(PATH.concat("/{id}"), user.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

}