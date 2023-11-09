package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.*;
import com.alsab.boozycalc.exception.NoCocktailInMenuException;
import com.alsab.boozycalc.exception.NoIngredientsForCocktailException;
import com.alsab.boozycalc.service.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartyService {
    public static int MAX_AVAILABLE = 99999;

    private final PartyDataService partyDataService;
    private final MenuDataService menuDataService;
    private final CocktailDataService cocktailDataService;
    private final OrderDataService orderDataService;
    private final PurchaseDataService purchaseDataService;
    private final RecipeDataService recipeDataService;
    private final UserDataService userDataService;
    private final OrderEntryDataService orderEntryDataService;

    public PartyDto add(PartyDto dto) {
        return partyDataService.add(dto);
    }

    public PartyDto edit(PartyDto dto) {
        partyDataService.findById(dto.getId());
        return partyDataService.edit(dto);
    }

    public List<PurchaseDto> getPartyPurchases(Long id) {
        PartyDto party = partyDataService.findById(id);
        return purchaseDataService.findAllByParty(party.getId());
    }

    public List<PartyMenuEntryDto> getPartyMenu(Long id) {
        PartyDto party = partyDataService.findById(id);
        List<MenuDto> menu = menuDataService.findAllByParty(party.getId());
        return menu.stream().map(x -> getCocktailInfo(party, x.getCocktail())).toList();
    }

    public PartyMenuEntryDto getCocktailInfo(PartyDto party, CocktailDto cocktail) {
        List<RecipeDto> recipe = recipeDataService.findAllByCocktail(cocktail.getId());

        float price = 0.0f;
        int available = MAX_AVAILABLE;
        for (var entry : recipe) {
            price += getIngredientPrice(party, entry.getIngredient()) * entry.getQuantity();
            available = Math.min(
                    available,
                    (int) (Math.floor(getIngredientAvailableQuantity(party, entry.getIngredient()) / entry.getQuantity()))
            );
        }

        return new PartyMenuEntryDto(cocktail, recipe, available, price);
    }

    public float getIngredientPrice(PartyDto party, IngredientDto ingredient) {
        List<PurchaseDto> purchases = purchaseDataService.findAllByParty(party.getId());
        float price = 0.0f;
        for (var purch : purchases) {
            if (Objects.equals(purch.getProduct().getIngredient().getId(), ingredient.getId()))
                price = Math.max(price, purch.getProduct().getPrice());
        }
        return price;
    }

    public float getIngredientAvailableQuantity(PartyDto party, IngredientDto ingredient) {
        List<PurchaseDto> purchases = purchaseDataService.findAllByParty(party.getId());
        float available = 0f;
        for (var purch : purchases) {
            if (Objects.equals(purch.getProduct().getIngredient().getId(), ingredient.getId()))
                available += purch.getQuantity();
        }
        return available;
    }

    public OrderEntryDto createOrder(Long partyId, Long userId, Long cocktailId) {
        PartyDto party = partyDataService.findById(partyId);
        UserDto user = userDataService.findById(userId);
        CocktailDto cocktail = cocktailDataService.findById(cocktailId);

        List<MenuDto> menu = menuDataService.findAllByParty(partyId);
        if (menu.stream().noneMatch(x -> Objects.equals(x.getCocktail().getId(), cocktailId)))
            throw new NoCocktailInMenuException(partyId, cocktailId);

        PartyMenuEntryDto cocktailInfo = getCocktailInfo(party, cocktail);

        if (cocktailInfo.getAvailable() <= 0) throw new NoIngredientsForCocktailException(partyId, cocktailId);

        useIngredients(party, cocktail);
        OrderDto order = new OrderDto();
        order.setPrice(0.0f);
        order.setParty(party);
        order.setPerson(user);
        if (!orderDataService.existsByPartyAndUser(party, user)) {
            order.setPrice(order.getPrice() + cocktailInfo.getPrice());
            order = orderDataService.add(order);
        } else {
            order = orderDataService.findByPartyAndUser(party, user);
            order.setPrice(order.getPrice() + cocktailInfo.getPrice());
            orderDataService.edit(order);
        }

        OrderEntryDto orderEntry = new OrderEntryDto();
        orderEntry.setOrder(order);
        orderEntry.setCocktail(cocktail);
        orderEntry.setName(cocktail.getName());
        if (!orderEntryDataService.existsById(orderEntry)) {
            orderEntry.setQuantity(1);
            orderEntry.setPriceForOne(cocktailInfo.getPrice());
            orderEntryDataService.add(orderEntry);
        } else {
            orderEntry = orderEntryDataService.findById(orderEntry);
            orderEntry.setQuantity(orderEntry.getQuantity() + 1);
            orderEntryDataService.edit(orderEntry);
        }
        return orderEntry;
    }

    public void useIngredients(PartyDto party, CocktailDto cocktail) {
        List<RecipeDto> recipe = recipeDataService.findAllByCocktail(cocktail.getId());
        List<PurchaseDto> purchases = purchaseDataService.findAllByParty(party.getId());

        for (var entry : recipe) {
            float q = entry.getQuantity();
            for (var purch : purchases) {
                if (Objects.equals(purch.getProduct().getIngredient().getId(), entry.getIngredient().getId())) {
                    float t = purch.getQuantity();
                    purch.setQuantity(Math.max(0, t - q));
                    purchaseDataService.edit(purch);
                    q = Math.max(0, q - t);
                    if (q == 0) break;
                }
            }
        }

    }

}