package com.alsab.boozycalc.relations;

import com.alsab.boozycalc.MockMvcTestContainersTest;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IngredientsAndTypesTest extends MockMvcTestContainersTest {
    public IngredientsAndTypesTest(WebApplicationContext webApplicationContext, IngredientRepo ingredientRepo, IngredientTypeRepo ingredientTypeRepo, EntityManagerFactory entityManagerFactory) {
        super(webApplicationContext, ingredientRepo, ingredientTypeRepo, entityManagerFactory);
    }

    @Test
    public void addEntity() {
        IngredientTypeEntity type1 = new IngredientTypeEntity();
        type1.setName("sour");
        IngredientTypeEntity type2 = new IngredientTypeEntity();
        type2.setName("tiki");
        super.getIngredientTypeRepo().save(type1);
        super.getIngredientTypeRepo().save(type2);

        int l = 0;
        for (IngredientTypeEntity entity : super.getIngredientTypeRepo().findAll()) {
            l += 1;
        }
        Assertions.assertEquals(l, 2);
    }

    @Test
    public void ingredientOfExistentTypeTest() throws Exception {
        IngredientTypeEntity type1 = new IngredientTypeEntity();
        type1.setName("tiki");
        type1.setId(super.getIngredientTypeRepo().save(type1).getId());

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
        type1.setId(super.getIngredientTypeRepo().save(type1).getId());

        final String body = """
                {
                    "id": 0,
                    "name": "rum",
                    "description": "для вас означает",
                    "type_id": 2
                }
                """;
        super.getMockMvc().perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest()).andDo(System.out::println);
    }
}
