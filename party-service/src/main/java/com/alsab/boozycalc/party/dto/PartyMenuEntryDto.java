package com.alsab.boozycalc.party.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyMenuEntryDto {
    CocktailDto cocktail;
    List<RecipeDto> recipe;
    int available;
    float price;
}
