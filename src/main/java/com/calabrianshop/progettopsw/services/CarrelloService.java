package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.*;
import com.calabrianshop.progettopsw.reporsitories.OrdineProdottoRepository;
import com.calabrianshop.progettopsw.reporsitories.OrdineRepository;
import com.calabrianshop.progettopsw.reporsitories.ProdottoInCarrelloRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CarrelloService {

    @Autowired
    private ProdottoInCarrelloRepository prodottoInCarrelloRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private OrdineProdottoRepository ordineProdottoRepository;

    @Transactional(readOnly = false)
    public void emptyCart() {
        Utente u = utenteService.getUtente();
        List<ProdottoInCarrello> car = (List<ProdottoInCarrello>) u.getCarrello();
        car.clear();
        entityManager.flush();
    }


    @Transactional(readOnly = false)
    public void rimuoviProdottoInCarrello( ProdottoInCarrello prodotto) {
        Utente u = utenteService.getUtente();
        prodotto.setUtente(u);
        u.getCarrello().remove(prodotto);
        System.out.println("prodotto rimosso" + prodotto.getProdotto().getNome());
        entityManager.flush();
    }

    private JsonNode getTokenNode() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        String jwtAsString;
        JsonNode jsonNode;
        try {
            jwtAsString = objectMapper.writeValueAsString(jwt);
            jsonNode = objectMapper.readTree(jwtAsString);
        } catch (JsonProcessingException e) {
            e.getMessage();
            throw new RuntimeException("Unable to retrieve user's info!");
        }
        return jsonNode;
    }

    @Transactional(readOnly = false)
    public ProdottoInCarrello aggiungiProdotto( ProdottoInCarrello prodotto) {
        System.out.println("user is " + getTokenNode().get("claims").get("email").asText());
        Utente u = utenteService.getUtente();
        System.out.println("utente is " + u.getId() + u.getNome() + u.getEmail() + u.getCarrello());
        prodotto.setUtente(u);
        System.out.println("Il prodotto da aggiungere è :" +prodotto.getProdotto().getNome());
       for (ProdottoInCarrello p : u.getCarrello()) {
            System.out.println("i prodotti nel carrello sono : " + p.getProdotto().getNome());
            if(p.equals(prodotto)) {
                System.out.println("I prodotti sono uguali");
                int newQuant = p.getQuantita() + prodotto.getQuantita();
                //if(newQuant>p.getProdotto().getQuantita())throw new IllegalStateException("non disponibile!");
                p.setQuantita(newQuant);
                return p;
            }
        }
        prodotto= prodottoInCarrelloRepository.save(prodotto);
        return prodotto;
    }

    @Transactional
    public List<ProdottoInCarrello> updateCarrello( List<ProdottoInCarrello> prodotti) {
        Utente u = utenteService.getUtente();
        u.getCarrello().clear();
        for (ProdottoInCarrello p : prodotti) {
            p.setUtente(u);
            p = prodottoInCarrelloRepository.save(p);
            u.getCarrello().add(p);
        }
        return (List<ProdottoInCarrello>) u.getCarrello();
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ordine registraOrdine(String ind) {
        Utente u = utenteService.getUtente();
        if (u.getCarrello().isEmpty()) throw new IllegalStateException();
        Ordine newOrdine = new Ordine();
        System.out.println(u.getEmail() + " " + u.getNome());
        newOrdine.setUtente(u);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd  HH:mm.ss").format(new Date());
        newOrdine.setData(timeStamp);
        newOrdine.setId(0);
        newOrdine.setTotale(0.0);
        newOrdine.setIndirizzo(ind);
        newOrdine.setOrdineProdottoCol(new LinkedList<>());
        newOrdine = ordineRepository.save(newOrdine);
        entityManager.flush();
        //  entityManager.lock(ProdottoInCarrello.class, LockModeType.OPTIMISTIC);
        for (ProdottoInCarrello p : prodottoInCarrelloRepository.findByUtente(u)) {
            Prodotto prod = entityManager.find(Prodotto.class, p.getProdotto().getId());
            //     entityManager.lock(Prodotto.class, LockModeType.OPTIMISTIC);
            OrdineProdotto op = new OrdineProdotto();
            op.setOrdine(newOrdine);
            op.setProdotto(prod);
            op.setQuantita(p.getQuantita());
            ordineProdottoRepository.save(op);
            newOrdine.setTotale(newOrdine.getTotale() + prod.getPrezzo() * p.getQuantita());
            //newOrdine.addProdotto(prod, p.getQuantita());
            //prod.setQuantita(prod.getQuantita()-p.getQuantita());
            //      entityManager.lock(Prodotto.class, LockModeType.NONE);
        }
        u.getCarrello().clear();
        // entityManager.lock(ProdottoInCarrello.class, LockModeType.NONE);
        entityManager.flush();
        return newOrdine;
    }


    @Transactional(readOnly = false)
    public List<ProdottoInCarrello> getProdottiCarrello(String email) {
        return prodottoInCarrelloRepository.findByUtente_Email(email);
    }

}