package com.alsab.boozycalc.party;

import com.alsab.boozycalc.party.config.WireMockConfig;
import com.alsab.boozycalc.party.wiremocks.CocktailMocks;
import com.alsab.boozycalc.party.wiremocks.ProductMocks;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@ActiveProfiles("test")
@Import(WireMockConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Getter
public abstract class MockMvcTestContainersTest implements Extension {
    private MockMvc mockMvc;
    private final WebApplicationContext webApplicationContext;
    private final EntityManagerFactory entityManagerFactory;
    private final WireMockServer mockCocktailService;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:latest:///test");
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        CocktailMocks.setupMockCocktailResponse(mockCocktailService, List.of(1, 2, 3));
        ProductMocks.setupMockProductResponse(mockCocktailService, List.of(1, 2, 3, 4, 5, 6));
    }

    @BeforeEach
    @Transactional
    public void truncateEverything() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Query query = entityManager
                .createNativeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'public'");
        final List tables = query.getResultList();
        entityManager.getTransaction().begin();
        tables.forEach(table -> {
                    if (table != "databasechangelog" && table != "databasechangeloglock") {
                        entityManager.createNativeQuery("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE").executeUpdate();
                    }
                }
        );
        entityManager.getTransaction().commit();
    }
}
