package com.projet.fip1.microsoft_store_back.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id_u;

 @Column(nullable = false)
 private String pseudo;

 @Column(nullable = false, unique = true)
 private String email;

 private String statut;
 private BigDecimal solde;

 @Column(columnDefinition = "TEXT")
 private String photo; // on stockera le fichier encodé en base64 ou le nom du fichier sur le disque

 @Column(nullable = false)
 private String mdp;

 public Long getId_u() {
  return id_u;
 }

 public void setId_u(Long id_u) {
  this.id_u = id_u;
 }

 public String getPseudo() {
  return pseudo;
 }

 public void setPseudo(String pseudo) {
  this.pseudo = pseudo;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getStatut() {
  return statut;
 }

 public void setStatut(String statut) {
  this.statut = statut;
 }

 public BigDecimal getSolde() {
  return solde;
 }

 public void setSolde(BigDecimal solde) {
  this.solde = solde;
 }

 public String getPhoto() {
  return photo;
 }

 public void setPhoto(String photo) {
  this.photo = photo;
 }

 public String getMdp() {
  return mdp;
 }

 public void setMdp(String mdp) {
  this.mdp = mdp;
 }

}
