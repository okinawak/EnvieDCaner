package com.projet.fip1.microsoft_store_back.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class LabelisationId implements Serializable {
    private Long id_a;
    private Long id_l;
}
