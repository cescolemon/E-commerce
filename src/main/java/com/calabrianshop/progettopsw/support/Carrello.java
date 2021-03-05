package com.calabrianshop.progettopsw.support;

import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;

import java.util.List;
import java.util.Objects;

public class Carrello {

    private List<ProdottoInCarrello> prodotti;
    private double totale;
    private int totNum;


    public Carrello(List<ProdottoInCarrello> prodotti) {

        this.prodotti = prodotti;
        this.totale=0;
        this.totNum=0;
        for(ProdottoInCarrello p : prodotti){
            totale+=p.getQuantita()*p.getProdotto().getPrezzo();
            totNum+=p.getQuantita();
        }
    }

    public List<ProdottoInCarrello> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<ProdottoInCarrello> prodotti) {
        this.prodotti = prodotti;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public int getTotNum() {
        return totNum;
    }

    public void setTotNum(int totNum) {
        this.totNum = totNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrello carrello = (Carrello) o;
        return Objects.equals(prodotti, carrello.prodotti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodotti);
    }
}
