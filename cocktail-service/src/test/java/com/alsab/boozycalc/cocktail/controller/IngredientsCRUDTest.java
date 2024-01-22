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
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
public class IngredientsCRUDTest extends MockMvcTestContainersTest {
    private final IngredientTypeRepo ingredientTypeRepo;

    private final IngredientTypeDataService ingredientTypeDataService;
    private final IngredientDataService ingredientDataService;

    @Autowired
    public IngredientsCRUDTest(WebApplicationContext webApplicationContext,
                               EntityManagerFactory entityManagerFactory,
                               IngredientTypeRepo ingredientTypeRepo,
                               IngredientController controller,
                               IngredientTypeDataService ingredientTypeDataService,
                               IngredientDataService ingredientDataService
    ) {
        super(webApplicationContext, entityManagerFactory);
        this.controller = controller;
        this.ingredientTypeRepo = ingredientTypeRepo;
        this.ingredientTypeDataService = ingredientTypeDataService;
        this.ingredientDataService = ingredientDataService;
    }

    private List<IngredientTypeDto> ingredientTypes;
    private List<IngredientDto> ingredients;

    private final IngredientController controller;
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
                    ingr.setId(ingredientDataService.add(ingr).map(IngredientDto::getId).block());
                    return ingr;
                }).toList();
    }

    @Test
    public void getAll() throws Exception {
        createIngredientTypes();
        createIngredients();
        int responseSize = controller.getAllIngredients().getBody().collectList().block().size();
        Assertions.assertEquals(ingredients.size(), responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getPage() throws Exception {
        createIngredientTypes();
        createIngredients();
        int responseSize = controller.getAllIngredientsWithPagination(0).getBody().collectList().block().size();
        Assertions.assertEquals(ingredients.size(), responseSize);
        System.out.println("Response size is " + responseSize);
    }

    @Test
    public void getEmptyPage() throws Exception {
        createIngredientTypes();
        createIngredients();
        int responseSize = controller.getAllIngredientsWithPagination(1).getBody().collectList().block().size();
        Assertions.assertEquals(0, responseSize);
        System.out.println("Response size is " + responseSize);
    }
    @Test
    public void getNegativePage() throws Exception {
        createIngredientTypes();
        createIngredients();
        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), controller.getAllIngredientsWithPagination(-1).getStatusCode());
        System.out.println("Response is "+ controller.getAllIngredientsWithPagination(-1).getStatusCode());
    }

    @Test
    public void ingredientOfExistentTypeTest() throws Exception {
        createIngredientTypes();
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(0L);
        ingredientDto.setName("rum");
        ingredientDto.setDescription("");
        ingredientDto.setType(ingredientTypes.get(0));

        ResponseEntity<Mono<?>> response = controller.addNewIngredient(ingredientDto);
        System.out.println("Response of addNewIngredient is "+ response.getBody().block().toString());

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        System.out.println("Response of addNewIngredient is "+ response.getBody().block().toString());
    }

    @Test
    public void ingredientOfNonExistentTypeTest() throws Exception {
        createIngredientTypes();
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(0L);
        ingredientDto.setName("rum");
        ingredientDto.setDescription("");
        ingredientDto.setType(ingredientTypes.get(2));

        ResponseEntity<Mono<?>> response = controller.addNewIngredient(ingredientDto);

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        System.out.println("Response of ingredientOfNonExistentTypeTest is "+ response.getBody().block().toString());

    }

    @Test
    public void ingredientWithTakenName() throws Exception {
        createIngredientTypes();
        createIngredients();

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(0L);
        ingredientDto.setName("White Rum");
        ingredientDto.setDescription("");
        ingredientDto.setType(ingredientTypes.get(0));

        ResponseEntity<Mono<?>> response = controller.addNewIngredient(ingredientDto);

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
        System.out.println("Response of ingredientWithTakenName is "+ response.getBody().block().toString());

    }

    @Test
    public void ingredientEdit() throws Exception {
        createIngredientTypes();
        createIngredients();

        IngredientDto whiteRum = ingredients.get(0);

        whiteRum.setName("Dark Rum");
        whiteRum.setDescription("Matured in charred oak barrels");
        whiteRum.setType(whiteRum.getType());

        ResponseEntity<Mono<?>> response = controller.editIngredient(whiteRum);

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(ingredientDataService.findById(whiteRum.getId()).getName(), "Dark Rum");

        System.out.println("ingredientDelete: " + response.getBody().block().toString());
    }

    @Test
    public void ingredientNotExistingEdit() throws Exception {
        createIngredientTypes();

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(1001L);
        ingredientDto.setName("Dark Rum");
        ingredientDto.setDescription("Matured in charred oak barrels");
        ingredientDto.setType(ingredientTypes.get(0));

        ResponseEntity<Mono<?>> response = controller.editIngredient(ingredientDto);

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());

        System.out.println("ingredientDelete: " + response.getBody().block().toString());

    }

    @Test
    public void ingredientTakenNameEdit() throws Exception {
        createIngredientTypes();
        createIngredients();

        IngredientDto whiteRum = ingredients.get(0);
        whiteRum.setId(whiteRum.getId());
        whiteRum.setName("Vodka");
        whiteRum.setDescription("Matured in charred oak barrels");
        whiteRum.setType(whiteRum.getType());

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), controller.editIngredient(whiteRum).getStatusCode());

    }

    @Test
    public void ingredientDelete() throws Exception {
        createIngredientTypes();
        createIngredients();
        ResponseEntity<Mono<?>> response = controller.deleteIngredient(1L);

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(StreamSupport.stream(ingredientDataService.findAll().toStream().spliterator(), false).count(), ingredients.size() - 1);
        System.out.println("ingredientDelete: " + ingredientDataService.findAll().map(IngredientDto::getId).blockFirst());
    }

    @Test
    public void ingredientNonExistentDelete() throws Exception {
        createIngredientTypes();
        createIngredients();
        ResponseEntity<Mono<?>> response = controller.deleteIngredient(1000L);

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
        System.out.println("ingredientDelete: " + response.getBody().block().toString());
    }
}
