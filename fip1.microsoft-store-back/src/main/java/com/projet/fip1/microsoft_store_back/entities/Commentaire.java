package com.projet.fip1.microsoft_store_back.entities;

import jakarta.persistence.*;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_c;

    @Column(columnDefinition = "TEXT")
    private String contenu;
    
    private int note;

    @Column(name = "id_u")
    private Long id_u;

    // Constructeurs
    public Commentaire() {}

    public Commentaire(String contenu, int note, Long id_u) {
        this.contenu = contenu;
        this.note = note;
        this.id_u = id_u;
    }

    // Getters et setters
    public Long getId_c() {
        return id_c;
    }

    public void setId_c(Long id_c) {
        this.id_c = id_c;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Long getId_u() {
        return id_u;
    }

    public void setId_u(Long id_u) {
        this.id_u = id_u;
    }
}