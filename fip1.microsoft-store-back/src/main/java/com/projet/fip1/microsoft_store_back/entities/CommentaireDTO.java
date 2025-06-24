package com.projet.fip1.microsoft_store_back.entities;

public class CommentaireDTO {
    private Long id_c;
    private String contenu;
    private int note;
    private Long id_u;
    private String pseudoUtilisateur;
    private Long id_a;

    public CommentaireDTO() {}

    public CommentaireDTO(Long id_c, String contenu, int note, Long id_u, String pseudoUtilisateur, Long id_a) {
        this.id_c = id_c;
        this.contenu = contenu;
        this.note = note;
        this.id_u = id_u;
        this.pseudoUtilisateur = pseudoUtilisateur;
        this.id_a = id_a;
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

    public String getPseudoUtilisateur() {
        return pseudoUtilisateur;
    }

    public void setPseudoUtilisateur(String pseudoUtilisateur) {
        this.pseudoUtilisateur = pseudoUtilisateur;
    }

    public Long getId_a() {
        return id_a;
    }

    public void setId_a(Long id_a) {
        this.id_a = id_a;
    }
}