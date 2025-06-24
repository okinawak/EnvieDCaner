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

    public Long getId_a() {
        return id_a;
    }

    public void setId_a(Long id_a) {
        this.id_a = id_a;
    }

    public Long getId_l() {
        return id_l;
    }

    public void setId_l(Long id_l) {
        this.id_l = id_l;
    }

    @ManyToOne
    @JoinColumn(name = "id_a", insertable = false, updatable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "id_l", insertable = false, updatable = false)
    private Label label;
}
