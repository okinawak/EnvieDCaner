package com.projet.fip1.microsoft_store_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.fip1.microsoft_store_back.entities.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByIdAIn(List<Long> ids);


    @Query("SELECT  a \n" + //
                "FROM Possession p\n" + //
                "JOIN Labelisation l1 ON p.id.id_a = l1.id_a\n" + //
                "JOIN Labelisation l2 ON l1.id_l = l2.id_l\n" + //
                "JOIN Application a ON l2.id_a = a.idA\n" + //
                "WHERE p.id.id_u = :utilisateurID\n" + //
                "AND a.idA NOT IN (\n" + //
                "    SELECT p2.id.id_a FROM Possession p2 WHERE p2.id.id_u = :utilisateurID\n" + //
                "  )")
    List<Application> getAppRecommandation(@Param("utilisateurID") Long userId);

    List<Application> findByIdAuteur(Long idU);

}
