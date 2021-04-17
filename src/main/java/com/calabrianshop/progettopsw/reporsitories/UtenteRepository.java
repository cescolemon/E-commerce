package com.calabrianshop.progettopsw.reporsitories;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.TreeSet;


@Repository
public interface UtenteRepository extends JpaRepository<Utente,Integer> {
    Utente findById(int id);
    Utente findByEmail(String email);
    void deleteById(int id);
    boolean existsById(int id);
    boolean existsByEmail(String email);

    }
