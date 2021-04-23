package com.calabrianshop.progettopsw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Ordine {
    private String data;
    private int id;
    private double totale;
    private String indirizzo;

    @Basic
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    @Basic
    @Column(name = "totale")
    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return id == ordine.id &&
                Double.compare(ordine.totale, totale) == 0 &&
                Objects.equals(data, ordine.data)&& Objects.equals(indirizzo, ordine.indirizzo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(data, id, totale,indirizzo);
    }

    private Utente utente;
    @JsonIgnore
    @ManyToOne( optional = false)
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    private Collection<OrdineProdotto> ordineProdottoCol;

    @OneToMany(mappedBy = "ordine")
    public Collection<OrdineProdotto> getOrdineProdottoCol() {
        return ordineProdottoCol;
    }

    public void setOrdineProdottoCol(Collection<OrdineProdotto> ordineProdottoCol) {
        this.ordineProdottoCol = ordineProdottoCol;
    }

    private Bolla bolla;

    @OneToOne
    public Bolla getBolla() {
        return bolla;
    }

    public void setBolla(Bolla bolla) {
        this.bolla = bolla;
    }
}
