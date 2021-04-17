package com.calabrianshop.progettopsw.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Utente {
    private String nome;
    private int id;
    private String email;
    private String password;

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
    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return id == utente.id &&
                Objects.equals(nome, utente.nome) &&
                Objects.equals(email, utente.email) &&
                Objects.equals(password, utente.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nome, id, email, password);
    }

    private Collection<ProdottoInCarrello> carrello;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utente")
    public Collection<ProdottoInCarrello> getCarrello() {
        return carrello;
    }

    public void setCarrello(Collection<ProdottoInCarrello> carrello) {
        this.carrello = carrello;
    }

    private Collection<Ordine> ordini;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utente")
    public Collection<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(Collection<Ordine> ordini) {
        this.ordini = ordini;
    }


private Collection<Bolla> bolle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utente")public Collection<Bolla> getBolle() {
    return bolle;
}public void setBolle(Collection<Bolla> bolle) {
    this.bolle = bolle;
}}
