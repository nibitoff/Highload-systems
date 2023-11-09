package com.alsab.boozycalc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntryEntity {
    @EmbeddedId
    private OrderEntryId id;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price_for_one")
    private int priceForOne;
}
