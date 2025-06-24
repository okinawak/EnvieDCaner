package com.projet.fip1.microsoft_store_back.entities;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_a")
    private Long id_a;

    private String nom;
    private String version;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "note_de_mise_a_jour", columnDefinition = "TEXT")
    private String note_de_mise_a_jour;

    private BigDecimal prix;

    @Column(columnDefinition = "TEXT") // ou TEXT LONG si tu as énormément d'images
    private String logo; // <- Contiendra l'image encodée en base64

    @Column(name = "id_auteur")
    private Long idAuteur;

    @Column(name = "git_username")
    private String gitUsername;

    @Column(name = "git_password")
    private String gitPassword;

    public String getGitUsername() {
        return gitUsername;
    }

    public void setGitUsername(String gitUsername) {
        this.gitUsername = gitUsername;
    }

    public String getGitPassword() {
        return gitPassword;
    }

    public void setGitPassword(String gitPassword) {
        this.gitPassword = gitPassword;
    }



    public Long getId_a() {
        return id_a;
    }

    public void setId_a(Long id_a) {
        this.id_a = id_a;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote_de_mise_a_jour() {
        return note_de_mise_a_jour;
    }

    public void setNote_de_mise_a_jour(String note_de_mise_a_jour) {
        this.note_de_mise_a_jour = note_de_mise_a_jour;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getId_auteur() {
        return idAuteur;
    }

    public void setId_auteur(Long id_auteur) {
        this.idAuteur = id_auteur;
    }
}

