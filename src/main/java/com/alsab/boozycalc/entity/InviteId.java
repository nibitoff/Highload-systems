package com.alsab.boozycalc.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Embeddable
public class InviteId {
    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private UserEntity person;

    public InviteId(PartyEntity party, UserEntity person) {
        this.party = party;
        this.person = person;
    }

    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
    }

    public UserEntity getPerson() {
        return person;
    }

    public void setPerson(UserEntity person) {
        this.person = person;
    }
}
