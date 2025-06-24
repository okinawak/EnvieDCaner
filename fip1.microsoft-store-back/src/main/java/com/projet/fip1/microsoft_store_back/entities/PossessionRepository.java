package com.projet.fip1.microsoft_store_back.entities;


import com.projet.fip1.microsoft_store_back.entities.Possession;
import com.projet.fip1.microsoft_store_back.entities.PossessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PossessionRepository extends JpaRepository<Possession, PossessionId> {
    boolean existsById(PossessionId id);
    @Query("SELECT p.id.id_a FROM Possession p WHERE p.id.id_u = :userId")
    List<Long> findAppIdsByUserId(@Param("userId") Long userId);
}
