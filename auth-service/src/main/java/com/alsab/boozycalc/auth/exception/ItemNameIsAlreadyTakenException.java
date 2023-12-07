package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class ItemNameIsAlreadyTakenException extends RuntimeException{
    private final String name;
    private final Class<?> itemClass;
    private final String description;

    public ItemNameIsAlreadyTakenException(Class<?> itemClass, String name){
        this.name = name;
        this.itemClass = itemClass;
        this.description = String.format("Item [" + itemClass.getSimpleName() + "] with name \"" + name + "\" already exists");
    }
}
