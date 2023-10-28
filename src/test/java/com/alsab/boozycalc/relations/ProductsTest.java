package com.alsab.boozycalc.relations;

import com.alsab.boozycalc.MockMvcTestContainersTest;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductsTest extends MockMvcTestContainersTest {
    private final IngredientTypeRepo ingredientTypeRepo;
    private final IngredientRepo ingredientRepo;

    public ProductsTest(WebApplicationContext webApplicationContext, EntityManagerFactory entityManagerFactory, IngredientTypeRepo ingredientTypeRepo, IngredientRepo ingredientRepo) {
        super(webApplicationContext, entityManagerFactory);
        this.ingredientTypeRepo = ingredientTypeRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Test
    public void addProductOfExistentIngredient() throws Exception{
        IngredientTypeEntity type = new IngredientTypeEntity();
        type.setName("distillate");
        type = ingredientTypeRepo.save(type);

        IngredientEntity ingr = new IngredientEntity();
        ingr.setName("rum");
        ingr.setType(type);
        ingr = ingredientRepo.save(ingr);

        final String body = """
                {
                    "name": "Bacardi",
                    "ingredient": {
                        "id": 1,
                        "name": "rum",
                        "type": {
                            "id": 1,
                            "name": "distillate"
                        }
                    },
                    "price": 1.0
                }
                """;

        super.getMockMvc().perform(post("/api/v1/products/add").contentType(MediaType.APPLICATION_JSON).content(body)).andDo(System.out::println).andExpect(status().isOk());
        super.getMockMvc().perform(get("/api/v1/products/all")).andDo(System.out::println).andExpect(status().isOk());
    }
}
