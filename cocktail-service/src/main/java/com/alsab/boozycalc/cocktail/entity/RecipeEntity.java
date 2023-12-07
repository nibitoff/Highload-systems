package com.alsab.boozycalc.cocktail.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class RecipeEntity {
    @EmbeddedId
    private RecipeId id;

    @Column(name = "quantity")
    private float quantity;

    public RecipeEntity(){

    }

    public RecipeEntity(RecipeId id, float quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public RecipeId getId() {
        return id;
    }

    public void setId(RecipeId id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
