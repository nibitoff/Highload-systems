package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.*;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import com.alsab.boozycalc.party.service.data.MenuDataService;
import com.alsab.boozycalc.party.service.data.PartyDataService;
import com.alsab.boozycalc.party.service.data.PurchaseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {
    public static int MAX_AVAILABLE = 99999;

    private final PartyDataService partyDataService;
    private final MenuDataService menuDataService;
    private final PurchaseDataService purchaseDataService;
    private final RecipeFeignService recipeFeignService;
    private final FeignCocktailServiceClient feignCocktailServiceClient;

    public List<PartyMenuEntryDto> getPartyMenu(Long id) {
        PartyDto party = partyDataService.findById(id);
        List<MenuDto> menu = menuDataService.findAllByParty(party.getId());
        return menu.stream().map(x -> getCocktailInfo(party, x.getCocktail())).toList();
    }

    public PartyMenuEntryDto getCocktailInfo(PartyDto party, CocktailDto cocktail) {

//        PartyDto party = partyDataService.findById(party_id);
//        CocktailDto cocktail = feignCocktailServiceClient.findById(cocktail_id);

        List<RecipeDto> recipe = recipeFeignService.findAllByCocktail(cocktail.getId());

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
