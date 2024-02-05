package com.mintos.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @Column(name = "iso", unique = true)
    private String iso;

    public Currency() {
    }

    public Currency(String iso) {
        this.iso = iso;
    }


    public Long getId() {
        return id;
    }

    public String getIso() {
        return iso;
    }
}
