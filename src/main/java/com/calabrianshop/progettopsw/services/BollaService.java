package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Bolla;
import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.reporsitories.BollaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;



@Service
public class BollaService {

   @Autowired
    private BollaRepository bollaRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UtenteService utenteService;

    @Transactional(readOnly = false)
    public Bolla generaBolla(Ordine ordine){
        if(bollaRepository.existsByOrdine(ordine)) return bollaRepository.findByOrdine(ordine);
        Bolla b= new Bolla();
        Utente u= utenteService.getUtente();
        b.setId(0);
        b.setOrdine(ordine);
        b.setUtente(u);
        bollaRepository.save(b);
        return b;
    }




}
