package com.calabrianshop.progettopsw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bolla {
    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bolla bolla = (Bolla) o;
        return id == bolla.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


    private Ordine ordine;

    @OneToOne
    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }


private Utente utente;

    @JsonIgnore
    @ManyToOne
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
}}
