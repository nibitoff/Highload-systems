package com.alsab.boozycalc.dto;

import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.PartyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private PartyDto party;
    private CocktailDto cocktail;
}
