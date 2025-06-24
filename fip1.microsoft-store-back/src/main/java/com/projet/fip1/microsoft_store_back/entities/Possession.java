package com.projet.fip1.microsoft_store_back.entities;
import com.projet.fip1.microsoft_store_back.entities.PossessionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Possession {

    @EmbeddedId
    private PossessionId id;

    public PossessionId getId() {
        return id;
    }

    public void setId(PossessionId id) {
        this.id = id;
    }
}
