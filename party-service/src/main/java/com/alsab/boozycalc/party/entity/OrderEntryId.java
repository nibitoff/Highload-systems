package com.alsab.boozycalc.party.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntryId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Long cocktail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntryId that = (OrderEntryId) o;
        return Objects.equals(order, that.order) && Objects.equals(cocktail, that.cocktail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, cocktail);
    }
}
