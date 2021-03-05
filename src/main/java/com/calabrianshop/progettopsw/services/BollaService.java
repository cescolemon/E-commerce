package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Bolla;
import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.reporsitories.BollaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.security.Principal;

@Service
public class BollaService {

    @Autowired
    private BollaRepository bollaRepository;

    @Autowired
    EntityManager entityManager;

    @Transactional(readOnly = false)
    public Bolla generaBolla(Principal user, Ordine ordine, String indirizzo){
        Bolla b= new Bolla();
        int id=ordine.getId();
        b.setId(id);
        b.setIndirizzo(indirizzo);
        b.setOrdine(ordine);
        return b;
    }

}
