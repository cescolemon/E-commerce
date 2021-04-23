package com.calabrianshop.progettopsw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

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

    @JsonIgnore
    @ManyToOne
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    private Prodotto prodotto;

    @ManyToOne
    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdottoInCarrello prodottoInCarrello = (ProdottoInCarrello) o;
        System.out.println(this.utente+" "
                + prodottoInCarrello.getUtente().getEmail()+
                "\n"+this.getProdotto().getNome()+" "
                +prodottoInCarrello.getProdotto().getNome());
        return this.utente.equals(prodottoInCarrello.getUtente()) && this.getProdotto().equals(prodottoInCarrello.getProdotto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utente, prodotto, quantita);
    }
}
