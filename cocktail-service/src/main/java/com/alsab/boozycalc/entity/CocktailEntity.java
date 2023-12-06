package com.alsab.boozycalc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cocktails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocktailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "steps")
    private String steps;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private CocktailTypeEntity type;
}
