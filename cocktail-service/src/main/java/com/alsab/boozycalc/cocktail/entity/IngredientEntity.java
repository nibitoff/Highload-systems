package com.alsab.boozycalc.cocktail.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class IngredientEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;
        @Column(name = "name", unique = true)
        private  String name;
        @Column(name = "description")
        private  String description;
        @ManyToOne
        @JoinColumn(name = "type", referencedColumnName = "id")
        private IngredientTypeEntity type;

        public IngredientEntity(){

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

        public IngredientTypeEntity getType() {
                return type;
        }

        public void setType(IngredientTypeEntity type) {
                this.type = type;
        }
}
