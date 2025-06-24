package com.projet.fip1.microsoft_store_back.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommentaireAssociationId implements Serializable {
    private Long id_a;
    private Long id_c;

    // Constructeurs
    public CommentaireAssociationId() {}

    public CommentaireAssociationId(Long id_a, Long id_c) {
        this.id_a = id_a;
        this.id_c = id_c;
    }

    // Getters et setters
    public Long getId_a() {
        return id_a;
    }

    public void setId_a(Long id_a) {
        this.id_a = id_a;
    }

    public Long getId_c() {
        return id_c;
    }

    public void setId_c(Long id_c) {
        this.id_c = id_c;
    }

    // equals et hashCode obligatoires pour les clés composées
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentaireAssociationId)) return false;
        CommentaireAssociationId that = (CommentaireAssociationId) o;
        return Objects.equals(id_a, that.id_a) &&
                Objects.equals(id_c, that.id_c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_a, id_c);
    }
}