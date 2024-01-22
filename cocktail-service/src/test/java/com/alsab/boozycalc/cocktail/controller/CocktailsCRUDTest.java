package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.MockMvcTestContainersTest;
import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.dto.CocktailTypeDto;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.dto.IngredientTypeDto;
import com.alsab.boozycalc.cocktail.service.data.CocktailDataService;
import com.alsab.boozycalc.cocktail.service.data.CocktailTypeDataService;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import com.alsab.boozycalc.cocktail.service.data.IngredientTypeDataService;
import jakarta.persistence.EntityManagerFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
public class CocktailsCRUDTest extends MockMvcTestContainersTest {
    private final CocktailController controller;
    private final IngredientTypeDataService ingredientTypeDataService;
    private final IngredientDataService ingredientDataService;
    private final CocktailTypeDataService cocktailTypeDataService;
    private final CocktailDataService cocktailDataService;

    @Autowired
    public CocktailsCRUDTest(
            WebApplicationContext webApplicationContext,
            EntityManagerFactory entityManagerFactory,
            CocktailController controller, IngredientTypeDataService ingredientTypeDataService,
            IngredientDataService ingredientDataService,
            CocktailTypeDataService cocktailTypeDataService,
            CocktailDataService cocktailDataService
    ) {
        super(webApplicationContext, entityManagerFactory);
        this.controller = controller;
        this.ingredientTypeDataService = ingredientTypeDataService;
        this.ingredientDataService = ingredientDataService;
        this.cocktailTypeDataService = cocktailTypeDataService;
        this.cocktailDataService = cocktailDataService;
    }


    private List<IngredientTypeDto> ingredientTypes;
    private List<IngredientDto> ingredients;
    private List<CocktailTypeDto> cocktailTypes;
    private List<CocktailDto> cocktails;


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

    private void createCocktails() {
        this.cocktailTypes =
                Stream.of(
                        "sour",
                        "tiki",
                        "duo",
                        "highball"
                ).map(x -> {
                    CocktailTypeDto type = new CocktailTypeDto();
                    type.setName(x);
                    type.setId(cocktailTypeDataService.add(type).getId());
                    return type;
                }).toList();

        this.cocktails =
                Stream.of(
                        new CocktailDto(null, "Daiquiri", "", "", cocktailTypes.get(0)),
                        new CocktailDto(null, "Screwdriver", "", "", cocktailTypes.get(2)),
                        new CocktailDto(null, "Cuba Libre", "", "", cocktailTypes.get(3))
                ).map(x -> {
                    CocktailDto cocktail = new CocktailDto();
                    cocktail.setName(x.getName());
                    cocktail.setType(x.getType());
                    cocktail.setId(cocktailDataService.add(cocktail).map(CocktailDto::getId).block());
                    return cocktail;
                }).toList();
    }

    @Test
    public void getAll() throws Exception {
        createIngredients();
        createCocktails();
        int responseSize = controller.getAllCocktails().getBody().collectList().block().size();
        Assertions.assertEquals(cocktails.size(), responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getPage() throws Exception {
        createIngredients();
        createCocktails();
        int responseSize = controller.getAllCocktailsWithPagination(0).getBody().collectList().block().size();
        Assertions.assertEquals(cocktails.size(), responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getEmptyPage() throws Exception {
        createIngredients();
        createCocktails();
        int responseSize = controller.getAllCocktailsWithPagination(1).getBody().collectList().block().size();
        Assertions.assertEquals(0, responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getNegativePage() throws Exception {
        createIngredients();
        createCocktails();
        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), controller.getAllCocktailsWithPagination(-1).getStatusCode());
        System.out.println("Response is "+ controller.getAllCocktailsWithPagination(-1).getStatusCode());
    }
}
