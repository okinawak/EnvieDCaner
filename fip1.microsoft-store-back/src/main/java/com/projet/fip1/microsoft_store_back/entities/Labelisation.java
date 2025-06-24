package com.projet.fip1.microsoft_store_back.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@IdClass(LabelisationId.class)
public class Labelisation {

    @Id
    private Long id_a;

    @Id
    private Long id_l;

    @ManyToOne
    @JoinColumn(name = "id_a", insertable = false, updatable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "id_l", insertable = false, updatable = false)
    private Label label;
}
