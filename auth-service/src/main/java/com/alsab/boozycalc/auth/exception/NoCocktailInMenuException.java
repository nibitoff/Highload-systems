package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class NoCocktailInMenuException extends RuntimeException{
    private final Long party_id;
    private final Long cocktali_id;
    private final String description;

    public NoCocktailInMenuException(Long party_id, Long cocktail_id){
        this.party_id = party_id;
        this.cocktali_id = cocktail_id;
        this.description = String.format("Cocktail with id " + getCocktali_id() + " is not in menu of party with id " + getParty_id());
    }
}
