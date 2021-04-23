package com.calabrianshop.progettopsw.reporsitories;

import com.calabrianshop.progettopsw.entities.Bolla;
import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BollaRepository extends JpaRepository<Bolla,Integer> {

    Bolla findById(int id);
    Bolla findByOrdine(Ordine ordine);
    List<Bolla> findByUtente(Utente utente);
    boolean existsById(int id);
    boolean existsByOrdine(Ordine ordine);

}
