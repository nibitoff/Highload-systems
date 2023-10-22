package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public record IngredientEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        String name,
        String description,
        Long type
){
}
