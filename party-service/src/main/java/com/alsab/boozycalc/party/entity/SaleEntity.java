package com.alsab.boozycalc.party.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sales")
@Data
public class SaleEntity {
    @EmbeddedId
    private SaleId id;
    @Column(name = "amount")
    private float amount;
    @Column(name = "description")
    private String description;
}
