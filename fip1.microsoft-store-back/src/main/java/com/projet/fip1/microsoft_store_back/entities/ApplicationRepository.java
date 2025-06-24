package com.projet.fip1.microsoft_store_back.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Utilisation d'une requête JPQL personnalisée pour éviter les problèmes de nommage
    @Query("SELECT a FROM Application a WHERE a.idA IN :ids")
    List<Application> findByIdAIn(@Param("ids") List<Long> ids);
    
    // Cette méthode utilise aussi une requête personnalisée pour la cohérence
    @Query("SELECT a FROM Application a WHERE a.idAuteur = :idAuteur")
    List<Application> findByIdAuteur(@Param("idAuteur") Long idAuteur);
}