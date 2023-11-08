package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.entity.RecipeId;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.repository.CocktailRepo;
import com.alsab.boozycalc.repository.MenuRepo;
import com.alsab.boozycalc.repository.PartyRepo;
import com.alsab.boozycalc.service.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyDataService partyDataService;
    private final MenuDataService menuDataService;
    private final CocktailDataService cocktailDataService;
    private final AuthenticationService authenticationService;
    private final InviteDataService inviteDataService;
    private final OrderDataService orderDataService;

    public PartyDto add(PartyDto dto){
        return partyDataService.add(dto);
    }

    public PartyDto edit(PartyDto dto){
        partyDataService.findById(dto.getId());
        return partyDataService.edit(dto);
    }


}