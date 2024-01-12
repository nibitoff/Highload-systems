package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.*;
import com.alsab.boozycalc.party.exception.NoCocktailInMenuException;
import com.alsab.boozycalc.party.exception.NoIngredientsForCocktailException;
import com.alsab.boozycalc.party.service.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.alsab.boozycalc.party.service.MenuService.MAX_AVAILABLE;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyDataService partyDataService;
    private final PurchaseDataService purchaseDataService;
    private final RecipeFeignService recipeFeignService;
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

    public PartyMenuEntryDto getCocktailInfo(PartyDto party, CocktailDto cocktail) {
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



}