package com.projet.fip1.microsoft_store_back.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
       // Récupérer tous les commentaires d'une application
    @Query("SELECT c FROM Commentaire c WHERE c.id_u = :userId")
    List<Commentaire> findByIdU(@Param("userId") Long userId);
    
    // Récupérer les commentaires d'une application avec les informations de l'utilisateur
    @Query("SELECT new com.projet.fip1.microsoft_store_back.entities.CommentaireDTO(" +
           "c.id_c, c.contenu, c.note, c.id_u, u.pseudo, ca.id.id_a) " +
           "FROM Commentaire c " +
           "JOIN Utilisateur u ON c.id_u = u.id_u " +
           "JOIN CommentaireAssociation ca ON c.id_c = ca.id.id_c " +
           "WHERE ca.id.id_a = :appId " +
           "ORDER BY c.id_c DESC")
    List<CommentaireDTO> findCommentairesByApplicationId(@Param("appId") Long appId);
    
    // Calculer la note moyenne d'une application
    @Query("SELECT AVG(c.note) FROM Commentaire c " +
           "JOIN CommentaireAssociation ca ON c.id_c = ca.id.id_c " +
           "WHERE ca.id.id_a = :appId")
    Double findAverageRatingByApplicationId(@Param("appId") Long appId);
    
    // Compter le nombre de commentaires d'une application
    @Query("SELECT COUNT(c) FROM Commentaire c " +
           "JOIN CommentaireAssociation ca ON c.id_c = ca.id.id_c " +
           "WHERE ca.id.id_a = :appId")
    Long countCommentairesByApplicationId(@Param("appId") Long appId);
}