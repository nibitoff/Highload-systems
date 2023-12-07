package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class NoIngredientsForCocktailException extends RuntimeException{
    private final Long party_id;
    private final Long cocktali_id;
    private final String description;

    public NoIngredientsForCocktailException(Long party_id, Long cocktail_id){
        this.party_id = party_id;
        this.cocktali_id = cocktail_id;
        this.description = String.format("Not enough ingredients for cocktail with id " + getCocktali_id() + " from party with id " + getParty_id());
    }
}
