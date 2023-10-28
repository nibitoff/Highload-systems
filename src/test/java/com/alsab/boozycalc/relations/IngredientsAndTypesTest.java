package com.alsab.boozycalc.relations;

import com.alsab.boozycalc.MockMvcTestContainersTest;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class IngredientsAndTypesTest extends MockMvcTestContainersTest {
    private final IngredientTypeRepo ingredientTypeRepo;

    @Autowired
    public IngredientsAndTypesTest(WebApplicationContext webApplicationContext, EntityManagerFactory entityManagerFactory, IngredientTypeRepo ingredientTypeRepo) {
        super(webApplicationContext, entityManagerFactory);
        this.ingredientTypeRepo = ingredientTypeRepo;
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
        for (IngredientTypeEntity entity : ingredientTypeRepo.findAll()) {
            l += 1;
        }
        Assertions.assertEquals(l, 2);
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

        super.getMockMvc().perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk()).andDo(System.out::println);
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
                    "type_id": 1
                }
                """;
        super.getMockMvc().perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest()).andDo(System.out::println);
    }
}
