package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.MockMvcTestContainersTest;
import com.alsab.boozycalc.cocktail.dto.*;
import com.alsab.boozycalc.cocktail.service.data.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@DirtiesContext
public class ProductsCRUDTest extends MockMvcTestContainersTest {
    private final IngredientTypeDataService ingredientTypeDataService;
    private final IngredientDataService ingredientDataService;
    private final ProductDataService productDataService;

    @Autowired
    public ProductsCRUDTest(
            WebApplicationContext webApplicationContext,
            EntityManagerFactory entityManagerFactory,
            IngredientTypeDataService ingredientTypeDataService,
            IngredientDataService ingredientDataService,
            ProductDataService productDataService
    ) {
        super(webApplicationContext, entityManagerFactory);
        this.ingredientTypeDataService = ingredientTypeDataService;
        this.ingredientDataService = ingredientDataService;
        this.productDataService = productDataService;
    }

    private List<IngredientTypeDto> ingredientTypes;
    private List<IngredientDto> ingredients;
    private List<ProductDto> products;

    private void createIngredients() {
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

    private void createProducts() {
        this.products =
                Stream.of(
                        new ProductDto(null, "Bacardi Blanco", "", ingredients.get(0), 1.57f),
                        new ProductDto(null, "Orthodox", "", ingredients.get(1), 0.8f),
                        new ProductDto(null, "Sady Pridonia Premium orange", "", ingredients.get(2), 0.15f),
                        new ProductDto(null, "Homemade lemon", "", ingredients.get(3), 0.224f),
                        new ProductDto(null, "Barinoff simple syrup", "", ingredients.get(4), 0.381f),
                        new ProductDto(null, "Coca-cola", "", ingredients.get(5), 0.111f)
                ).map(x -> {
                    ProductDto product = new ProductDto();
                    product.setName(x.getName());
                    product.setPrice(x.getPrice());
                    product.setIngredient(x.getIngredient());
                    product.setId(productDataService.add(product).getId());
                    return product;
                }).toList();
    }

    @Test
    public void getAll() throws Exception {
        createIngredients();
        createProducts();
        super.getMockMvc()
                .perform(get("/api/v1/products/all"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(products.size())));
    }

    @Test
    public void getPage() throws Exception {
        createIngredients();
        createProducts();
        super.getMockMvc()
                .perform(get("/api/v1/products/all/0"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(products.size())));
    }

    @Test
    public void getEmptyPage() throws Exception {
        createIngredients();
        createProducts();
        super.getMockMvc()
                .perform(get("/api/v1/products/all/1"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(0)));
    }

    @Test
    public void getNegativePage() throws Exception {
        createIngredients();
        createProducts();
        super.getMockMvc()
                .perform(get("/api/v1/products/all/-1"))
                .andExpect(status().isBadRequest());
    }
}
