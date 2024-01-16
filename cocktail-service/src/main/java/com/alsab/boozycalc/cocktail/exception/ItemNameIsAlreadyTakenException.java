package com.alsab.boozycalc.cocktail.exception;

import lombok.Getter;

@Getter
public class ItemNameIsAlreadyTakenException extends RuntimeException{
    private final String description;

    public ItemNameIsAlreadyTakenException(Class<?> itemClass, String name){
        this.description = String.format("Item [" + itemClass.getSimpleName() + "] with name \"" + name + "\" already exists");
    }
}
