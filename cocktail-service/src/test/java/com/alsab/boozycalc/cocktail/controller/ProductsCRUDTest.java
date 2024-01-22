package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.MockMvcTestContainersTest;
import com.alsab.boozycalc.cocktail.dto.*;
import com.alsab.boozycalc.cocktail.service.data.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@DirtiesContext
public class ProductsCRUDTest extends MockMvcTestContainersTest {
    private final IngredientTypeDataService ingredientTypeDataService;
    private final IngredientDataService ingredientDataService;
    private final ProductDataService productDataService;
    private final ProductController controller;

    @Autowired
    public ProductsCRUDTest(
            WebApplicationContext webApplicationContext,
            EntityManagerFactory entityManagerFactory,
            IngredientTypeDataService ingredientTypeDataService,
            IngredientDataService ingredientDataService,
            ProductDataService productDataService,
            ProductController controller) {
        super(webApplicationContext, entityManagerFactory);
        this.controller = controller;
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
                    ingr.setId(ingredientDataService.add(ingr).map(IngredientDto::getId).block());
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
                    product.setId(productDataService.add(product).map(ProductDto::getId).block());
                    return product;
                }).toList();
    }

    @Test
    public void getAll() throws Exception {
        createIngredients();
        createProducts();
        int responseSize = controller.getProducts().getBody().collectList().block().size();
        Assertions.assertEquals(products.size(), responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getPage() throws Exception {
        createIngredients();
        createProducts();
        int responseSize = controller.getAllProductsWithPagination(0).getBody().collectList().block().size();
        Assertions.assertEquals(products.size(), responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getEmptyPage() throws Exception {
        createIngredients();
        createProducts();
        int responseSize = controller.getAllProductsWithPagination(1).getBody().collectList().block().size();
        Assertions.assertEquals(0, responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getNegativePage() throws Exception {
        createIngredients();
        createProducts();
        ResponseEntity<Flux<ProductDto>> response = controller.getAllProductsWithPagination(-1);
        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
        System.out.println("Response is "+ response.getStatusCode());
    }
}
