package com.projet.fip1.microsoft_store_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.fip1.microsoft_store_back.entities.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    
}
