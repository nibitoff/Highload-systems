package com.alsab.boozycalc.cocktail.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredient_types")
public class IngredientTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public IngredientTypeEntity() {
    }

    public IngredientTypeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
