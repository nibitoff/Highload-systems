package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.*;
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
    private final AuthenticationService authenticationService;
    private final InviteDataService inviteDataService;
    private final OrderDataService orderDataService;
    private final PurchaseDataService purchaseDataService;
    private final RecipeDataService recipeDataService;
    private final IngredientDataService ingredientDataService;

    public PartyDto add(PartyDto dto) {
        return partyDataService.add(dto);
    }

    public PartyDto edit(PartyDto dto) {
        partyDataService.findById(dto.getId());
        return partyDataService.edit(dto);
    }
    public List<PurchaseDto> getPartyPurchases(Long id){
        PartyDto party = partyDataService.findById(id);
        return purchaseDataService.findAllByParty(party.getId());
    }

    public List<PartyMenuEntryDto> getPartyMenu(Long id){
        PartyDto party = partyDataService.findById(id);
        List<MenuDto> menu = menuDataService.findAllByParty(party.getId());
        return menu.stream().map(x -> getCocktailInfo(party, x.getCocktail())).toList();
    }

    public PartyMenuEntryDto getCocktailInfo(PartyDto party, CocktailDto cocktail) {

        List<PurchaseDto> purchases = purchaseDataService.findAllByParty(party.getId());
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

}