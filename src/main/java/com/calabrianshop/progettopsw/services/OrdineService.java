package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.OrdineProdotto;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.reporsitories.OrdineProdottoRepository;
import com.calabrianshop.progettopsw.reporsitories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private UtenteService clienteService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrdineProdottoRepository ordineProdottoRepository;

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniUtente(Principal user){
        Utente u= clienteService.getUtente(user);
        return ordineRepository.findByUtente(u);
    }

    @Transactional(readOnly = true)
    public List<OrdineProdotto> getProdottiOrdinati(Principal user, Ordine ordine){
        Optional<Ordine> o= ordineRepository.findById(ordine.getId());
        if(!o.isPresent())throw new NoSuchElementException("Ordine non presente nel database!");
        return ordineProdottoRepository.findByOrdine(o.get());
    }

    @Transactional(readOnly = true)
    public String getData(Ordine ordine){
        entityManager.find(Ordine.class, ordine);
        String res=ordine.getData().getDay()+"/"+ordine.getData().getMonth()+"/"+ordine.getData().getYear();
        System.out.println(res);
        return res;
    }
}
