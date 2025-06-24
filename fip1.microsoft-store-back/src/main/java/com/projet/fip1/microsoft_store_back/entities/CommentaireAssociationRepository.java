package com.projet.fip1.microsoft_store_back.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentaireAssociationRepository extends JpaRepository<CommentaireAssociation, CommentaireAssociationId> {
    
    @Modifying
    @Transactional
    @Query("DELETE FROM CommentaireAssociation ca WHERE ca.id.id_c = :commentaireId")
    void deleteByIdIdC(@Param("commentaireId") Long commentaireId);
}