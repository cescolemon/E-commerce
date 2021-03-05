package com.calabrianshop.progettopsw.reporsitories;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine,Integer> {

    Optional<Ordine> findById(int id);
    List<Ordine> findByUtente(Utente u);
    List<Ordine> findByData(Date data);
    List<Ordine>findByUtenteAndData(Utente u, Date data);

}
