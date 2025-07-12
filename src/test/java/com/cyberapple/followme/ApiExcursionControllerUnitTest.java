package com.cyberapple.followme;

import com.cyberapple.followme.configuration.ApiControllerUnitTestConfiguration;
import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.security.TokenBlackListService;
import com.cyberapple.followme.services.ExcursionService;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.cyberapple.followme.api.controllers.ApiExcursionController;

@WebMvcTest(controllers = ApiExcursionController.class)
@ActiveProfiles("test")
@Import({ApiControllerUnitTestConfiguration.class })
class ApiExcursionControllerUnitTest {
    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    ExcursionService excursionService;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    TokenBlackListService tokenBlackListService;

    @MockitoBean
    EntityManagerFactory entityManagerFactory;

    @Test
    void contextLoads() {
        mockMvcTester.get()
            .uri("/api/excursions")
            .accept(MediaType.APPLICATION_JSON)
            .assertThat()
            .hasStatus(HttpStatus.OK);
    }
}
