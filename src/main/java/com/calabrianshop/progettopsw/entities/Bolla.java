package com.calabrianshop.progettopsw.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bolla {
    private int id;
    private String indirizzo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "indirizzo")
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bolla bolla = (Bolla) o;
        return id == bolla.id &&
                Objects.equals(indirizzo, bolla.indirizzo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, indirizzo);
    }


    private Ordine ordine;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }
}
