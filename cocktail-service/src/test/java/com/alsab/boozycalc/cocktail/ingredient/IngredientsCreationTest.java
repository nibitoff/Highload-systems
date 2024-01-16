package com.alsab.boozycalc.cocktail.ingredient;

import com.alsab.boozycalc.cocktail.MockMvcTestContainersTest;
import com.alsab.boozycalc.cocktail.entity.IngredientTypeEntity;
import com.alsab.boozycalc.cocktail.repository.IngredientTypeRepo;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@DirtiesContext
public class IngredientsCreationTest extends MockMvcTestContainersTest {

    private final IngredientTypeRepo ingredientTypeRepo;

    @Autowired
    public IngredientsCreationTest(WebApplicationContext webApplicationContext,
                                   EntityManagerFactory entityManagerFactory,
                                   IngredientTypeRepo ingredientTypeRepo) {
        super(webApplicationContext, entityManagerFactory);
        this.ingredientTypeRepo = ingredientTypeRepo;
    }

    @Test
    public void ingredientOfExistentTypeTest() throws Exception {

        IngredientTypeEntity type1 = new IngredientTypeEntity();
        type1.setName("Distilled beverage");
        type1.setId(ingredientTypeRepo.save(type1).getId());

        System.out.println(type1.getId());

        final String body = """
                {
                    "id": 0,
                    "name": "rum",
                    "description": "",
                    "type": {
                        "id": 1
                    }
                }
                """;

        super.getMockMvc()
                .perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }
}
