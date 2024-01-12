package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.*;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.exception.NoCocktailInMenuException;
import com.alsab.boozycalc.party.exception.NoIngredientsForCocktailException;
import com.alsab.boozycalc.party.service.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDataService orderDataService;
    private final AuthFeignService authFeignService;
    private final PartyDataService partyDataService;
    private final CocktailFeignService cocktailFeignService;
    private final RecipeFeignService recipeFeignService;
    private final PartyService partyService;
    private final MenuDataService menuDataService;
    private final PurchaseDataService purchaseDataService;
    private final OrderEntryDataService orderEntryDataService;

    public boolean existsByPartyAndUser(PartyDto party, UserDto user) {
        try {
            orderDataService.findByPartyAndUser(party, user);
            return true;
        } catch (ItemNotFoundException e){
            return false;
        }
    }

    public void useIngredients(PartyDto party, CocktailDto cocktail) {
        List<RecipeDto> recipe = recipeFeignService.findAllByCocktail(cocktail.getId());
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

    public OrderEntryDto createOrder(Long partyId, Long userId, Long cocktailId) {
        PartyDto party = partyDataService.findById(partyId);
        UserDto user = authFeignService.findById(userId);
        CocktailDto cocktail = cocktailFeignService.findById(cocktailId);

        List<MenuDto> menu = menuDataService.findAllByParty(partyId);
        if (menu.stream().noneMatch(x -> Objects.equals(x.getCocktail().getId(), cocktailId)))
            throw new NoCocktailInMenuException(partyId, cocktailId);

        PartyMenuEntryDto cocktailInfo = partyService.getCocktailInfo(party, cocktail);

        if (cocktailInfo.getAvailable() <= 0) throw new NoIngredientsForCocktailException(partyId, cocktailId);

        useIngredients(party, cocktail);
        OrderDto order = new OrderDto();
        order.setPrice(0.0f);
        order.setParty(party);
        order.setPerson(user);
        if (existsByPartyAndUser(party, user)) {
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
}
