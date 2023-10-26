package com.alsab.boozycalc.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private IngredientEntity ingredient_id;
    @Column(name = "price")
    private float price;

    public ProductEntity(){

    }

    public ProductEntity(String name, String description, IngredientEntity ingredientEntity, float price) {
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

    public IngredientEntity getIngredientId() {
        return ingredient_id;
    }

    public void setIngredientId(IngredientEntity ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}