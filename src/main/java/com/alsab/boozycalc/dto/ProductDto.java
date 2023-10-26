package com.alsab.boozycalc.dto;

public record ProductDto (
     Long id,
     String name,
     String description,
     Long ingredient_id,
     float price
){
}
