package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.OrdineProdotto;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.reporsitories.OrdineProdottoRepository;
import com.calabrianshop.progettopsw.reporsitories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
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
    public List<Ordine> getOrdiniUtente( HttpServletRequest user){
        Utente u= clienteService.getUtente(user);
        return ordineRepository.findByUtente(u);
    }

    @Transactional(readOnly = true)
    public List<OrdineProdotto> getProdottiOrdinati(HttpServletRequest user, Ordine ordine){
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
