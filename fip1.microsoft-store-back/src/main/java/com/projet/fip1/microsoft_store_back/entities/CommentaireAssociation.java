package com.projet.fip1.microsoft_store_back.entities;

import jakarta.persistence.*;

@Entity
public class CommentaireAssociation {

    @EmbeddedId
    private CommentaireAssociationId id;

    // Constructeurs
    public CommentaireAssociation() {}

    public CommentaireAssociation(CommentaireAssociationId id) {
        this.id = id;
    }

    // Getters et setters
    public CommentaireAssociationId getId() {
        return id;
    }

    public void setId(CommentaireAssociationId id) {
        this.id = id;
    }
}