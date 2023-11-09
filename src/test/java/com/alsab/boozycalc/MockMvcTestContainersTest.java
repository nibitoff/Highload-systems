package com.alsab.boozycalc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@RequiredArgsConstructor
@Getter
public abstract class MockMvcTestContainersTest implements Extension {
    private MockMvc mockMvc;
    private final WebApplicationContext webApplicationContext;
    private final EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void webApplicationEnvironmentTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("cocktailController"));
    }

    @Transactional
    @AfterEach
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
