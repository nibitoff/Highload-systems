package com.alsab.boozycalc.party.dto;

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
