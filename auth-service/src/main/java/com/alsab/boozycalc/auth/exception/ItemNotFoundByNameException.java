package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundByNameException extends RuntimeException{
    private final String name;
    private final Class<?> itemClass;
    private final String description;

    public ItemNotFoundByNameException(Class<?> itemClass, String name){
        this.name = name;
        this.itemClass = itemClass;
        this.description = String.format("No item [" + itemClass.getSimpleName() + "] with name \"" + name + "\"");
    }
}
