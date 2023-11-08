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
public class InviteId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private UserEntity person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InviteId inviteId = (InviteId) o;
        return Objects.equals(party, inviteId.party) && Objects.equals(person, inviteId.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party, person);
    }
}
