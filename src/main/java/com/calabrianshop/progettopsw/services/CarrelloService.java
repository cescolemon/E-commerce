package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.*;
import com.calabrianshop.progettopsw.reporsitories.OrdineProdottoRepository;
import com.calabrianshop.progettopsw.reporsitories.OrdineRepository;
import com.calabrianshop.progettopsw.reporsitories.ProdottoInCarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    public void emptyCart(Principal user) {
        Utente u = utenteService.getUtente(user);
        List<ProdottoInCarrello> car = (List<ProdottoInCarrello>) u.getCarrello();
        car.clear();
        entityManager.flush();
    }


    @Transactional(readOnly = false)
    public void rimuoviProdottoInCarrello(Principal user, ProdottoInCarrello prodotto) {
        Utente u = utenteService.getUtente(user);
        prodotto.setUtente(u);
        u.getCarrello().remove(prodotto);
        System.out.println("prodotto rimosso" + prodotto.getProdotto().getNome());
        entityManager.flush();
    }

    @Transactional(readOnly = false)
    public ProdottoInCarrello aggiungiProdotto(Principal user, ProdottoInCarrello prodotto) {
        System.out.println("user is " + user.getName());
        Utente u = utenteService.getUtente(user);
        System.out.println("utente is " + u.getId() + u.getNome() + u.getEmail() + u.getCarrello());
        prodotto.setUtente(u);
        System.out.println("Il prodotto da aggiungere è :" + prodotto.getProdotto());
        for (ProdottoInCarrello p : u.getCarrello()) {
            System.out.println("il prodotto è : " + p.getProdotto().getNome());
            if (p.equals(prodotto)) {
                System.out.println("I prodotti sono uguali");
                int newQuant = p.getQuantita() + prodotto.getQuantita();
                //if(newQuant>p.getProdotto().getQuantita())throw new IllegalStateException("non disponibile!");
                p.setQuantita(newQuant);
                return p;
            }
        }
        prodotto = prodottoInCarrelloRepository.save(prodotto);
        return prodotto;
    }

    @Transactional
    public List<ProdottoInCarrello> updateCarrello(Principal user, List<ProdottoInCarrello> prodotti) {
        Utente u = utenteService.getUtente(user);
        u.getCarrello().clear();
        for (ProdottoInCarrello p : prodotti) {
            p.setUtente(u);
            p = prodottoInCarrelloRepository.save(p);
            u.getCarrello().add(p);
        }
        return (List<ProdottoInCarrello>) u.getCarrello();
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ordine registraOrdine(Principal user) {
        Utente u = utenteService.getUtente(user);
        if (u.getCarrello().isEmpty()) throw new IllegalStateException();
        Ordine newOrdine = new Ordine();
        System.out.println(u.getEmail() + " " + u.getNome());
        newOrdine.setUtente(u);
        newOrdine.setData(Timestamp.from(Instant.now()));
        int id= 0;
        for(ProdottoInCarrello z: u.getCarrello()) id+=z.getId();
        newOrdine.setId(id+u.getId());
        newOrdine.setTotale(0.0);
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


    @Transactional(readOnly = true)
    public List<ProdottoInCarrello> getProdottiCarrello(String email) {
        return prodottoInCarrelloRepository.findByUtente_Email(email);
    }

}