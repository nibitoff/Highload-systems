package com.alsab.boozycalc.cocktail.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException{
    private final String description;

    public ItemNotFoundException(Class<?> itemClass, Long id){
        this.description = String.format("No item of [" + itemClass.getSimpleName() + "] with id " + id);
    }
}
