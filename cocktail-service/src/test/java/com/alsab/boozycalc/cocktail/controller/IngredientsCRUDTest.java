package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.MockMvcTestContainersTest;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.dto.IngredientTypeDto;
import com.alsab.boozycalc.cocktail.repository.IngredientTypeRepo;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import com.alsab.boozycalc.cocktail.service.data.IngredientTypeDataService;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@DirtiesContext
public class IngredientsCRUDTest extends MockMvcTestContainersTest {

    private final IngredientTypeRepo ingredientTypeRepo;
    private final IngredientTypeDataService ingredientTypeDataService;
    private final IngredientDataService ingredientDataService;

    @Autowired
    public IngredientsCRUDTest(WebApplicationContext webApplicationContext,
                               EntityManagerFactory entityManagerFactory,
                               IngredientTypeRepo ingredientTypeRepo,
                               IngredientTypeDataService ingredientTypeDataService,
                               IngredientDataService ingredientDataService
    ) {
        super(webApplicationContext, entityManagerFactory);
        this.ingredientTypeRepo = ingredientTypeRepo;
        this.ingredientTypeDataService = ingredientTypeDataService;
        this.ingredientDataService = ingredientDataService;
    }

    private List<IngredientTypeDto> ingredientTypes;
    private List<IngredientDto> ingredients;

    private void createIngredientTypes() {
        this.ingredientTypes =
                Stream.of(
                        "spirit",
                        "liquor",
                        "juice",
                        "syrup",
                        "drink"
                ).map(x -> {
                    IngredientTypeDto type = new IngredientTypeDto();
                    type.setName(x);
                    type.setId(ingredientTypeDataService.add(type).getId());
                    return type;
                }).toList();
    }

    private void createIngredients() {
        this.ingredients =
                Stream.of(
                        new IngredientDto(null, "White Rum", "", ingredientTypes.get(0)),
                        new IngredientDto(null, "Vodka", "", ingredientTypes.get(0)),
                        new IngredientDto(null, "Orange Juice", "", ingredientTypes.get(2)),
                        new IngredientDto(null, "Lemon Juice", "", ingredientTypes.get(2)),
                        new IngredientDto(null, "Simple Syrup", "", ingredientTypes.get(3)),
                        new IngredientDto(null, "Coke", "", ingredientTypes.get(4))
                ).map(x -> {
                    IngredientDto ingr = new IngredientDto();
                    ingr.setName(x.getName());
                    ingr.setType(x.getType());
                    ingr.setId(ingredientDataService.add(ingr).getId());
                    return ingr;
                }).toList();
    }

    @Test
    public void getAll() throws Exception {
        createIngredientTypes();
        createIngredients();
        super.getMockMvc()
                .perform(get("/api/v1/ingredients/all"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(ingredients.size())));
    }

    @Test
    public void getPage() throws Exception {
        createIngredientTypes();
        createIngredients();
        super.getMockMvc()
                .perform(get("/api/v1/ingredients/all/0"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(ingredients.size())));
    }

    @Test
    public void getEmptyPage() throws Exception {
        createIngredientTypes();
        createIngredients();
        super.getMockMvc()
                .perform(get("/api/v1/ingredients/all/1"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(0)));
    }

    @Test
    public void getNegativePage() throws Exception {
        createIngredientTypes();
        createIngredients();
        super.getMockMvc()
                .perform(get("/api/v1/ingredients/all/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ingredientOfExistentTypeTest() throws Exception {
        createIngredientTypes();

        final String body = String.format("""
                {
                    "id": 0,
                    "name": "rum",
                    "description": "",
                    "type": {
                        "id": %d
                    }
                }
                """, ingredientTypes.get(0).getId());

        super.getMockMvc()
                .perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void ingredientOfNonExistentTypeTest() throws Exception {
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ingredientWithTakenName() throws Exception {
        createIngredientTypes();
        createIngredients();

        final String body = String.format("""
                {
                    "id": 0,
                    "name": "White Rum",
                    "description": "",
                    "type": {
                        "id": %d
                    }
                }
                """, ingredientTypes.get(0).getId());

        super.getMockMvc()
                .perform(post("/api/v1/ingredients/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ingredientEdit() throws Exception {
        createIngredientTypes();
        createIngredients();

        IngredientDto whiteRum = ingredients.get(0);
        final String body = String.format("""
                {
                    "id": %d,
                    "name": "Dark Rum",
                    "description": "Matured in charred oak barrels",
                    "type": {
                        "id": %d
                    }
                }
                """, whiteRum.getId(), whiteRum.getType().getId());

        super.getMockMvc()
                .perform(post("/api/v1/ingredients/edit").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpectAll(status().isOk());
        Assertions.assertEquals(ingredientDataService.findById(whiteRum.getId()).getName(), "Dark Rum");
    }

    @Test
    public void ingredientNotExistingEdit() throws Exception {
        createIngredientTypes();
        final String body = """
                {
                    "id": 1001,
                    "name": "Dark Rum",
                    "description": "Matured in charred oak barrels",
                    "type": {
                        "id": 0
                    }
                }
                """;
        super.getMockMvc()
                .perform(post("/api/v1/ingredients/edit").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpectAll(status().isBadRequest()).andDo(r -> System.out.println(r.getResponse().getContentAsString()));
    }

    @Test
    public void ingredientTakenNameEdit() throws Exception {
        createIngredientTypes();
        createIngredients();

        IngredientDto whiteRum = ingredients.get(0);
        final String body = String.format("""
                {
                    "id": %d,
                    "name": "Vodka",
                    "description": "Matured in charred oak barrels",
                    "type": {
                        "id": %d
                    }
                }
                """, whiteRum.getId(), whiteRum.getType().getId());

        super.getMockMvc()
                .perform(post("/api/v1/ingredients/edit").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpectAll(status().isBadRequest());
    }

    @Test
    public void ingredientDelete() throws Exception {
        createIngredientTypes();
        createIngredients();
        super.getMockMvc()
                .perform(delete("/api/v1/ingredients/delete?id=1"))
                .andExpect(status().isOk());
        Assertions.assertEquals(StreamSupport.stream(ingredientDataService.findAll().spliterator(), false).count(), ingredients.size() - 1);
    }

    @Test
    public void ingredientNonExistentDelete() throws Exception {
        createIngredientTypes();
        createIngredients();
        super.getMockMvc()
                .perform(delete("/api/v1/ingredients/delete?id=1000"))
                .andExpect(status().isBadRequest());
        Assertions.assertEquals(StreamSupport.stream(ingredientDataService.findAll().spliterator(), false).count(), ingredients.size());
    }
}
