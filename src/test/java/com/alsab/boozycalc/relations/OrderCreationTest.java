package com.alsab.boozycalc.relations;

import com.alsab.boozycalc.MockMvcTestContainersTest;
import com.alsab.boozycalc.dto.*;
import com.alsab.boozycalc.entity.RoleEnum;
import com.alsab.boozycalc.service.PartyService;
import com.alsab.boozycalc.service.data.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class OrderCreationTest extends MockMvcTestContainersTest {

    private final PartyService partyService;
    private final IngredientTypeDataService ingredientTypeDataService;
    private final IngredientDataService ingredientDataService;
    private final ProductDataService productDataService;
    private final CocktailDataService cocktailDataService;
    private final CocktailTypeDataService cocktailTypeDataService;
    private final RecipeDataService recipeDataService;
    private final PartyDataService partyDataService;
    private final PurchaseDataService purchaseDataService;
    private final MenuDataService menuDataService;
    private final UserDataService userDataService;
    private final OrderDataService orderDataService;
    private final OrderEntryDataService orderEntryDataService;

    private List<ProductDto> products;
    private List<IngredientDto> ingredients;
    private List<CocktailDto> cocktails;
    private PartyDto party;
    private UserDto user;

    @Autowired
    public OrderCreationTest(WebApplicationContext webApplicationContext, EntityManagerFactory entityManagerFactory,
                             PartyService partyService,
                             IngredientTypeDataService ingredientTypeDataService,
                             IngredientDataService ingredientDataService,
                             ProductDataService productDataService,
                             CocktailDataService cocktailDataService,
                             RecipeDataService recipeDataService,
                             PartyDataService partyDataService,
                             PurchaseDataService purchaseDataService,
                             MenuDataService menuDataService,
                             CocktailTypeDataService cocktailTypeDataService,
                             UserDataService userDataService,
                             OrderEntryDataService orderEntryDataService,
                             OrderDataService orderDataService

    ) {
        super(webApplicationContext, entityManagerFactory);
        this.partyService = partyService;
        this.ingredientTypeDataService = ingredientTypeDataService;
        this.cocktailDataService = cocktailDataService;
        this.productDataService = productDataService;
        this.ingredientDataService = ingredientDataService;
        this.recipeDataService = recipeDataService;
        this.partyDataService = partyDataService;
        this.purchaseDataService = purchaseDataService;
        this.menuDataService = menuDataService;
        this.cocktailTypeDataService = cocktailTypeDataService;
        this.userDataService = userDataService;
        this.orderEntryDataService = orderEntryDataService;
        this.orderDataService = orderDataService;
    }

    private void createBase() {
        List<IngredientTypeDto> ingrTypes =
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

        List<IngredientDto> ingrs =
                Stream.of(
                        new IngredientDto(null, "White Rum", "", ingrTypes.get(0)),
                        new IngredientDto(null, "Vodka", "", ingrTypes.get(0)),
                        new IngredientDto(null, "Orange Juice", "", ingrTypes.get(2)),
                        new IngredientDto(null, "Lemon Juice", "", ingrTypes.get(2)),
                        new IngredientDto(null, "Simple Syrup", "", ingrTypes.get(3)),
                        new IngredientDto(null, "Coke", "", ingrTypes.get(4))
                ).map(x -> {
                    IngredientDto ingr = new IngredientDto();
                    ingr.setName(x.getName());
                    ingr.setType(x.getType());
                    ingr.setId(ingredientDataService.add(ingr).getId());
                    return ingr;
                }).toList();

        List<ProductDto> products =
                Stream.of(
                        new ProductDto(null, "Bacardi Blanco", "", ingrs.get(0), 1.57f),
                        new ProductDto(null, "Orthodox", "", ingrs.get(1), 0.8f),
                        new ProductDto(null, "Sady Pridonia Premium orange", "", ingrs.get(2), 0.15f),
                        new ProductDto(null, "Homemade lemon", "", ingrs.get(3), 0.224f),
                        new ProductDto(null, "Barinoff simple syrup", "", ingrs.get(4), 0.381f),
                        new ProductDto(null, "Coca-cola", "", ingrs.get(5), 0.111f)
                ).map(x -> {
                    ProductDto product = new ProductDto();
                    product.setName(x.getName());
                    product.setPrice(x.getPrice());
                    product.setIngredient(x.getIngredient());
                    product.setId(productDataService.add(product).getId());
                    return product;
                }).toList();

        List<CocktailTypeDto> cockTypes =
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

        List<CocktailDto> cocktails =
                Stream.of(
                        new CocktailDto(null, "Daiquiri", "", "", cockTypes.get(0)),
                        new CocktailDto(null, "Screwdriver", "", "", cockTypes.get(2)),
                        new CocktailDto(null, "Cuba Libre", "", "", cockTypes.get(3))
                ).map(x -> {
                    CocktailDto cocktail = new CocktailDto();
                    cocktail.setName(x.getName());
                    cocktail.setType(x.getType());
                    cocktail.setId(cocktailDataService.add(cocktail).getId());
                    return cocktail;
                }).toList();

        List<RecipeDto> recipes =
                Stream.of(
                        new RecipeDto(ingrs.get(0), cocktails.get(0), 60.0f),
                        new RecipeDto(ingrs.get(3), cocktails.get(0), 30.0f),
                        new RecipeDto(ingrs.get(4), cocktails.get(0), 15.0f),
                        new RecipeDto(ingrs.get(1), cocktails.get(1), 60.0f),
                        new RecipeDto(ingrs.get(2), cocktails.get(1), 150.0f),
                        new RecipeDto(ingrs.get(0), cocktails.get(2), 60.0f),
                        new RecipeDto(ingrs.get(5), cocktails.get(2), 150.0f)
                ).map(x -> {
                    RecipeDto recipe = new RecipeDto();
                    recipe.setCocktail(x.getCocktail());
                    recipe.setIngredient(x.getIngredient());
                    recipe.setQuantity(x.getQuantity());
                    recipeDataService.add(recipe);
                    return recipe;
                }).toList();

        PartyDto party = new PartyDto(null, "Test Party", new Timestamp(System.currentTimeMillis()), "Asus", "");
        party.setId(partyDataService.add(party).getId());

        List<MenuDto> menus =
                Stream.of(
                        new MenuDto(party, cocktails.get(0)),
                        new MenuDto(party, cocktails.get(2))
                ).map(x -> {
                    MenuDto menu = new MenuDto();
                    menu.setCocktail(x.getCocktail());
                    menu.setParty(x.getParty());
                    menuDataService.add(menu);
                    return menu;
                }).toList();

        UserDto user = new UserDto(null, "drinker", "123", "Igor Katamaranov", RoleEnum.USER);
        user.setId(userDataService.saveUser(user).getId());

        this.products = products;
        this.ingredients = ingrs;
        this.party = party;
        this.user = user;
        this.cocktails = cocktails;
    }

    private void createBasePurchases() {
        List<PurchaseDto> purchases =
                Stream.of(
                        new PurchaseDto(products.get(0), party, 200.0f),    // rum
                        new PurchaseDto(products.get(1), party, 500.0f),    // vodka
                        new PurchaseDto(products.get(2), party, 1000.0f),   // orange juice
                        new PurchaseDto(products.get(3), party, 60.0f),     // lemon juice
                        new PurchaseDto(products.get(4), party, 1000.0f),   // syrup
                        new PurchaseDto(products.get(5), party, 1000.0f)    // coke
                ).map(x -> {
                    PurchaseDto purchase = new PurchaseDto();
                    purchase.setProduct(x.getProduct());
                    purchase.setParty(x.getParty());
                    purchase.setQuantity(x.getQuantity());
                    purchaseDataService.add(purchase);
                    return purchase;
                }).toList();
    }

    @Test
    public void PurchasesChangedAfterOrderCreatedTest() throws Exception {
        createBase();
        createBasePurchases();

        Long cocktailId = 1L;
        String cocktailName = cocktailDataService.findById(cocktailId).getName();
        String productNameInPurchases = "Bacardi Blanco";
        final String url = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + cocktailId;

        float availableBefore = partyService.getPartyMenu(party.getId()).stream().filter(
                x -> x.getCocktail().getId().equals(cocktailId)
        ).findFirst().orElseThrow().getAvailable();

        float purchaseBeforeOrder = purchaseDataService.findAllByParty(party.getId()).stream().filter(
                x -> Objects.equals(x.getProduct().getName(), productNameInPurchases)
        ).findFirst().orElseThrow().getQuantity();

        // ---------------------- REQUEST ----------------------
        super.getMockMvc()
                .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        // ---------------------- ------- ----------------------

        float recipeQuantity = recipeDataService.findAllByCocktail(cocktailId)
                .stream().filter(
                        x -> x.getIngredient().getId().equals(
                                purchaseDataService.findAllByParty(party.getId()).stream().filter(
                                        y -> Objects.equals(y.getProduct().getName(), productNameInPurchases)
                                ).findFirst().orElseThrow().getProduct().getIngredient().getId())
                ).findFirst().orElseThrow().getQuantity();
        float purchaseAfterOrder = purchaseDataService.findAllByParty(party.getId()).stream().filter(
                x -> Objects.equals(x.getProduct().getName(), productNameInPurchases)
        ).findFirst().orElseThrow().getQuantity();

        float availableAfter = partyService.getPartyMenu(party.getId()).stream().filter(
                x -> x.getCocktail().getId().equals(cocktailId)
        ).findFirst().orElseThrow().getAvailable();

        System.out.println(productNameInPurchases + " before order: " + purchaseBeforeOrder + ", after order: " + purchaseAfterOrder + ", amount of ingredient in recipe: " + recipeQuantity);
        Assertions.assertEquals(purchaseAfterOrder, purchaseBeforeOrder - recipeQuantity);

        System.out.println("Before order there were enough ingredients for " + availableBefore + " cocktails" + cocktailName + " , and after order: " + availableAfter);
        Assertions.assertEquals(availableAfter, availableBefore - 1);
    }

    @Test
    public void OrderEntitiesCreatedAfterOrderCreationTest() throws Exception {
        createBase();
        createBasePurchases();

        Long cocktailId = 1L;
        String productNameInPurchases = "Bacardi Blanco";
        final String url = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + cocktailId;

        Assertions.assertTrue(orderDataService.findAll().isEmpty());
        Assertions.assertTrue(orderEntryDataService.findAll().isEmpty());

        float price = partyService.getPartyMenu(party.getId()).stream().filter(
                x -> x.getCocktail().getId().equals(cocktailId)
        ).findFirst().orElseThrow().getPrice();

        // ---------------------- REQUEST ----------------------
        super.getMockMvc()
                .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        // ---------------------- ------- ----------------------

        Assertions.assertEquals(orderDataService.findAll().size(), 1);
        Assertions.assertEquals(orderEntryDataService.findAll().size(), 1);

        OrderDto orderDto = orderDataService.findByPartyAndUser(party, user);
        Assertions.assertEquals(orderDto.getPrice(), price);

        OrderEntryDto orderEntry = new OrderEntryDto(orderDto, cocktailDataService.findById(cocktailId), "", 0, 0.0f);
        Assertions.assertTrue(orderEntryDataService.existsById(orderEntry));

        Assertions.assertEquals(orderEntryDataService.findById(orderEntry).getQuantity(), 1);

    }

    @Test
    public void CreateSeveralOrdersOfSameCocktailTest() throws Exception {
        createBase();
        createBasePurchases();

        Long cocktailId = 1L;
        final String url = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + cocktailId;

        float price = partyService.getPartyMenu(party.getId()).stream().filter(
                x -> x.getCocktail().getId().equals(cocktailId)
        ).findFirst().orElseThrow().getPrice();

        // ---------------------- REQUEST ----------------------
        super.getMockMvc()
                .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        super.getMockMvc()
                .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        // ---------------------- ------- ----------------------

        Assertions.assertEquals(orderDataService.findAll().size(), 1);
        Assertions.assertEquals(orderEntryDataService.findAll().size(), 1);

        OrderDto orderDto = orderDataService.findByPartyAndUser(party, user);
        Assertions.assertEquals(orderDto.getPrice(), price * 2);

        OrderEntryDto orderEntry = new OrderEntryDto(orderDto, cocktailDataService.findById(cocktailId), "", 0, 0.0f);
        Assertions.assertTrue(orderEntryDataService.existsById(orderEntry));
        Assertions.assertEquals(orderEntryDataService.findById(orderEntry).getQuantity(), 2);
    }

    @Test
    public void CreateSeveralOrdersOfDifferentCocktailTest() throws Exception {
        createBase();
        createBasePurchases();

        Long daiquiriId = 1L;
        Long cubaId = 3L;
        final String url1 = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + daiquiriId;
        final String url2 = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + cubaId;

        float priceDaiquiri = partyService.getPartyMenu(party.getId()).stream().filter(
                x -> x.getCocktail().getId().equals(daiquiriId)
        ).findFirst().orElseThrow().getPrice();

        float priceCuba = partyService.getPartyMenu(party.getId()).stream().filter(
                x -> x.getCocktail().getId().equals(cubaId)
        ).findFirst().orElseThrow().getPrice();

        // ---------------------- REQUEST ----------------------
        super.getMockMvc()
                .perform(post(url1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        super.getMockMvc()
                .perform(post(url1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        super.getMockMvc()
                .perform(post(url2).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        // ---------------------- ------- ----------------------

        Assertions.assertEquals(orderDataService.findAll().size(), 1);
        Assertions.assertEquals(orderEntryDataService.findAll().size(), 2);

        OrderDto orderDto = orderDataService.findByPartyAndUser(party, user);
        Assertions.assertEquals(orderDto.getPrice(), priceDaiquiri * 2 + priceCuba);

        OrderEntryDto orderEntry = new OrderEntryDto(orderDto, cocktailDataService.findById(daiquiriId), "", 0, 0.0f);
        Assertions.assertTrue(orderEntryDataService.existsById(orderEntry));
        Assertions.assertEquals(orderEntryDataService.findById(orderEntry).getQuantity(), 2);

        orderEntry = new OrderEntryDto(orderDto, cocktailDataService.findById(cubaId), "", 0, 0.0f);
        Assertions.assertTrue(orderEntryDataService.existsById(orderEntry));
        Assertions.assertEquals(orderEntryDataService.findById(orderEntry).getQuantity(), 1);
    }

    @Test
    public void BadRequestIfNotEnoughIngredientsTest() throws Exception {
        createBase();
        createBasePurchases();

        Long daiquiriId = 1L;
        final String url1 = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + daiquiriId;

        // ---------------------- REQUEST ----------------------
        super.getMockMvc()
                .perform(post(url1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        super.getMockMvc()
                .perform(post(url1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        super.getMockMvc()
                .perform(post(url1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Not enough ingredients for cocktail with id " + daiquiriId + " from party with id " + party.getId()));
        // ---------------------- ------- ----------------------
    }

    @Test
    public void BadRequestIfNotInTheMenuTest() throws Exception {
        createBase();
        createBasePurchases();

        long screwId = 2L;
        final String url1 = "/api/v1/parties/create?partyId=1&userId=1&cocktailId=" + screwId;

        // ---------------------- REQUEST ----------------------
        super.getMockMvc()
                .perform(post(url1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Cocktail with id " + screwId + " is not in menu of party with id " + party.getId()));
        // ---------------------- ------- ----------------------
    }
}
