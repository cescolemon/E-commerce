package com.calabrianshop.progettopsw.entities;

import javax.persistence.*;

@Entity
public class ProdottoInCarrello {
    private Integer id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer quantita;

    @Basic
    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    private Double subTotale;

    @Basic
    public Double getSubTotale() {
        return subTotale;
    }

    public void setSubTotale(Double subTotale) {
        this.subTotale = subTotale;
    }

    private Utente utente;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    private Prodotto prodotto;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
}
