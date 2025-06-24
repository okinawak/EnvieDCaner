package com.projet.fip1.microsoft_store_back.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Label {
    @Id
    private Long id_l;

    private String nom;

    @OneToMany(mappedBy = "label")
    private List<Labelisation> labelisations;
}
