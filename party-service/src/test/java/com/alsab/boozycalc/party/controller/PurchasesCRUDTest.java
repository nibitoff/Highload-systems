package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.MockMvcTestContainersTest;
import com.alsab.boozycalc.party.dto.*;
import com.alsab.boozycalc.party.service.data.PartyDataService;
import com.alsab.boozycalc.party.service.data.PurchaseDataService;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@DirtiesContext
public class PurchasesCRUDTest extends MockMvcTestContainersTest {
    private final PartyDataService partyDataService;
    private final PurchaseDataService purchaseDataService;

    @Autowired
    public PurchasesCRUDTest(
            WebApplicationContext webApplicationContext,
            EntityManagerFactory entityManagerFactory,
            WireMockServer mockCocktailService,
            PartyDataService partyDataService,
            PurchaseDataService purchaseDataService) {
        super(webApplicationContext, entityManagerFactory, mockCocktailService);
        this.partyDataService = partyDataService;
        this.purchaseDataService = purchaseDataService;
    }

    List<IngredientTypeDto> ingredientTypes;
    List<IngredientDto> ingredients;
    List<ProductDto> products;
    List<PartyDto> parties;
    List<PurchaseDto> purchases;

    private void createCocktailServiceBase() {
        AtomicInteger counter = new AtomicInteger(0);
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
                    type.setId((long) counter.getAndIncrement());
                    return type;
                }).toList();

        counter.set(0);
        this.ingredients =
                Stream.of(
                        new IngredientDto(1L, "White Rum", "", ingredientTypes.get(0)),
                        new IngredientDto(2L, "Vodka", "", ingredientTypes.get(0)),
                        new IngredientDto(3L, "Orange Juice", "", ingredientTypes.get(2)),
                        new IngredientDto(4L, "Lemon Juice", "", ingredientTypes.get(2)),
                        new IngredientDto(5L, "Simple Syrup", "", ingredientTypes.get(3)),
                        new IngredientDto(6L, "Coke", "", ingredientTypes.get(4))
                ).toList();

        counter.set(0);
        this.products =
                Stream.of(
                        new ProductDto(1L, "Bacardi Blanco", "", ingredients.get(0), 1.57f),
                        new ProductDto(2L, "Orthodox", "", ingredients.get(1), 0.8f),
                        new ProductDto(3L, "Sady Pridonia Premium orange", "", ingredients.get(2), 0.15f),
                        new ProductDto(4L, "Homemade lemon", "", ingredients.get(3), 0.224f),
                        new ProductDto(5L, "Barinoff simple syrup", "", ingredients.get(4), 0.381f),
                        new ProductDto(6L, "Coca-cola", "", ingredients.get(5), 0.111f)
                ).toList();
    }

    private void createParties() {
        this.parties = Stream.of(
                new PartyDto(null, "Repka Test Party", Timestamp.valueOf("2024-02-25 20:00:00"), "Repka", "")
        ).peek(x -> x.setId(partyDataService.add(x).getId())).toList();
    }

    private void createPurchases() {
        this.purchases =
                Stream.of(
                        new PurchaseDto(products.get(0), parties.get(0), 1000),
                        new PurchaseDto(products.get(1), parties.get(0), 1000),
                        new PurchaseDto(products.get(2), parties.get(0), 50),
                        new PurchaseDto(products.get(3), parties.get(0), 100),
                        new PurchaseDto(products.get(4), parties.get(0), 300)
                ).map(purchaseDataService::add).toList();
    }

    @Test
    public void getAll() throws Exception {
        createCocktailServiceBase();
        createParties();
        createPurchases();
        super.getMockMvc()
                .perform(get("/api/v1/purchases/all"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(purchases.size())));
    }

    @Test
    public void getPage() throws Exception {
        createCocktailServiceBase();
        createParties();
        createPurchases();
        super.getMockMvc()
                .perform(get("/api/v1/purchases/all/0"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(purchases.size())));
    }

    @Test
    public void getEmptyPage() throws Exception {
        createCocktailServiceBase();
        createParties();
        createPurchases();
        super.getMockMvc()
                .perform(get("/api/v1/purchases/all/1"))
                .andExpectAll(status().isOk(), jsonPath("$", hasSize(0)));
    }

    @Test
    public void getNegativePage() throws Exception {
        createCocktailServiceBase();
        createParties();
        createPurchases();
        super.getMockMvc()
                .perform(get("/api/v1/purchases/all/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void purchaseOfNonExistentProduct() throws Exception {
        createParties();
        final String body = """
                {
                    "product": {"id": 199},
                    "party": {"id": 1},
                    "quantity": 100
                }
                """;
        super.getMockMvc()
                .perform(post("/api/v1/purchases/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void purchaseOfNonExistentParty() throws Exception {
        createParties();
        final String body = """
                {
                    "product": {"id": 6},
                    "party": {"id": 0},
                    "quantity": 100
                }
                """;
        super.getMockMvc()
                .perform(post("/api/v1/purchases/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void purchaseNonPositiveQuantity() throws Exception {
        createParties();
        final String body = """
                {
                    "product": {"id": 6},
                    "party": {"id": 1},
                    "quantity": -10
                }
                """;
        super.getMockMvc()
                .perform(post("/api/v1/purchases/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void purchaseCorrectAdd() throws Exception {
        createParties();
        final String body = """
                {
                    "product": {"id": 6},
                    "party": {"id": 1},
                    "quantity": 100
                }
                """;
        super.getMockMvc()
                .perform(post("/api/v1/purchases/add").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }
}
