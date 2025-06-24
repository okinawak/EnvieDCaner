package com.projet.fip1.microsoft_store_back.entities;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PossessionId implements Serializable {
    private Long id_u;
    private Long id_a;

    // Constructeurs, equals() et hashCode() OBLIGATOIRES
    public PossessionId() {}

    public PossessionId(Long id_u, Long id_a) {
        this.id_u = id_u;
        this.id_a = id_a;
    }

    public Long getId_u() {
        return id_u;
    }

    public void setId_u(Long id_u) {
        this.id_u = id_u;
    }

    public Long getId_a() {
        return id_a;
    }

    public void setId_a(Long id_a) {
        this.id_a = id_a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PossessionId)) return false;
        PossessionId that = (PossessionId) o;
        return Objects.equals(id_u, that.id_u) &&
                Objects.equals(id_a, that.id_a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_u, id_a);
    }
}
