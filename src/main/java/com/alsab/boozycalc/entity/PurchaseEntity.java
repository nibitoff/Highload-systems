package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchases")
public class PurchaseEntity implements  Serializable{
    @EmbeddedId
    private PurchaseId id;
    @Column(name = "quantity")
    private float quantity;

    public PurchaseEntity() {
    }

    public PurchaseId getId() {
        return id;
    }

    public void setId(PurchaseId id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}