package com.calabrianshop.progettopsw.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.util.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Ordine {
    private Date data;
    private int id;
    private double totale;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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
                Objects.equals(data, ordine.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(data, id, totale);
    }

    private Utente utente;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    private Collection<OrdineProdotto> ordineProdottoCol;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordine")
    public Collection<OrdineProdotto> getOrdineProdottoCol() {
        return ordineProdottoCol;
    }

    public void setOrdineProdottoCol(Collection<OrdineProdotto> ordineProdottoCol) {
        this.ordineProdottoCol = ordineProdottoCol;
    }

    private Bolla bolla;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ordine", optional = false)
    public Bolla getBolla() {
        return bolla;
    }

    public void setBolla(Bolla bolla) {
        this.bolla = bolla;
    }
}
