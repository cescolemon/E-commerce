package com.calabrianshop.progettopsw.reporsitories;

import com.calabrianshop.progettopsw.entities.Bolla;
import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BollaRepository extends JpaRepository<Bolla,Integer> {

    Bolla findById(int id);
    Bolla findByOrdine(Ordine ordine);
    List<Bolla> findByUtente(Utente utente);
    boolean existsById(int id);

}
