package com.dimitar.booking.entities;

import jakarta.persistence.*;

@Entity
public class GroomingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameMk;
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "groomer_id")
    private Groomer groomer;


    public GroomingService() {
    }

    public GroomingService(String nameMk, String nameEn, Groomer groomer) {
        this.nameMk = nameMk;
        this.nameEn = nameEn;
        this.groomer = groomer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameMk() {
        return nameMk;
    }

    public void setNameMk(String nameMk) {
        this.nameMk = nameMk;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Groomer getGroomer() {
        return groomer;
    }

    public void setGroomer(Groomer groomer) {
        this.groomer = groomer;
    }
}