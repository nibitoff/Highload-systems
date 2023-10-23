package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cocktails")
public class CocktailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "recipe_description")
    private String recipe_description;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private CocktailTypeEntity type;

    public CocktailEntity() {
    }

    public CocktailEntity(String name, String description, String recipe, CocktailTypeEntity type) {
        this.name = name;
        this.description = description;
        this.recipe_description = recipe;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipe() {
        return recipe_description;
    }

    public void setRecipe(String recipe) {
        this.recipe_description = recipe;
    }

    public CocktailTypeEntity getType() {
        return type;
    }

    public void setType(CocktailTypeEntity type) {
        this.type = type;
    }
}
