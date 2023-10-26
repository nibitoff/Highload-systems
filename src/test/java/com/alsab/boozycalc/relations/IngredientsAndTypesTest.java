package com.alsab.boozycalc.relations;

import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class IngredientsAndTypesTest {

    private MockMvc mockMvc;
    private final WebApplicationContext webApplicationContext;

    private final IngredientRepo ingredientRepo;

    private final IngredientTypeRepo ingredientTypeRepo;

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public IngredientsAndTypesTest(WebApplicationContext webApplicationContext,
                                   IngredientRepo ingredientRepo,
                                   IngredientTypeRepo ingredientTypeRepo,
                                   EntityManagerFactory entityManagerFactory) {
        this.webApplicationContext = webApplicationContext;
        this.ingredientRepo = ingredientRepo;
        this.ingredientTypeRepo = ingredientTypeRepo;
        this.entityManagerFactory = entityManagerFactory;
    }



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

    @Test
    public void addEntity() {
        IngredientTypeEntity type1 = new IngredientTypeEntity();
        type1.setName("sour");
        IngredientTypeEntity type2 = new IngredientTypeEntity();
        type2.setName("tiki");
        ingredientTypeRepo.save(type1);
        ingredientTypeRepo.save(type2);

        int l = 0;
        Iterator<?> it = ingredientTypeRepo.findAll().iterator();
        while (it.hasNext()) {
            l += 1;
            it.next();
        }
        Assertions.assertEquals(l, 2);
    }


    @AfterEach
    public void truncateEverything() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Query query = entityManager
                .createNativeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'public'");
        final List tables = query.getResultList();
        entityManager.getTransaction().begin();
        tables.forEach(table -> {
                    if (table != "databasechangelog" && table != "databasechangeloglock") {
                        entityManager.createNativeQuery("TRUNCATE TABLE " + table + " CASCADE").executeUpdate();
                    }
                }
        );
        entityManager.getTransaction().commit();
    }

    @Test
    public void ingredientOfExistentTypeTest() throws Exception {
        IngredientTypeEntity type1 = new IngredientTypeEntity();
        type1.setName("tiki");
        type1.setId(ingredientTypeRepo.save(type1).getId());

        final String body = """
                {
                    "id": 0,
                    "name": "rum",
                    "description": "",
                    "type_id":
                """ + type1.getId() + "}";

        mockMvc.perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    public void ingredientOfNonExistentTypeTest() throws Exception {
        IngredientTypeEntity type1 = new IngredientTypeEntity();
        type1.setName("tiki");
        type1.setId(ingredientTypeRepo.save(type1).getId());

        final String body = """
                {
                    "id": 0,
                    "name": "rum",
                    "description": "для вас означает",
                    "type_id": 2
                }
                """;
        mockMvc.perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest()).andDo(System.out::println);
    }

}
