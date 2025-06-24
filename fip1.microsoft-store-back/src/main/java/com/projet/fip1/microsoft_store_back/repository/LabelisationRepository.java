package com.projet.fip1.microsoft_store_back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.projet.fip1.microsoft_store_back.entities.Label;
import com.projet.fip1.microsoft_store_back.entities.Labelisation;
import com.projet.fip1.microsoft_store_back.entities.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LabelisationRepository extends JpaRepository<Labelisation, Long>  {
    
}
