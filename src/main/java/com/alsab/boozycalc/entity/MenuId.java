package com.alsab.boozycalc.entity;
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
public class MenuId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party;

    @ManyToOne
    @JoinColumn(name = "cocktail_id", referencedColumnName = "id")
    private CocktailEntity cocktail;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MenuId menuId = (MenuId) obj;
        return cocktail.equals(menuId.cocktail) &&
                party.equals(menuId.party);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party, cocktail);
    }
}