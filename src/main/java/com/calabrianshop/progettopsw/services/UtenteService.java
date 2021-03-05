package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.reporsitories.UtenteRepository;
import com.calabrianshop.progettopsw.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Carrello getCarrello(Principal user){
        Utente u= getUtente(user);
        List<ProdottoInCarrello> prodotti = (List<ProdottoInCarrello>) u.getCarrello();
        return new Carrello(prodotti);
    }

    @Transactional(readOnly = true)
    public List<Utente> showAllUtente(){
        return utenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Utente getById(int id){
        return  utenteRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void deleteUtentebyId(int id){
        if(!utenteRepository.existsById(id)) return;
        utenteRepository.deleteById(id);
        System.out.println("deleted utente "+id);
        utenteRepository.flush();
    }

    @Transactional(readOnly = false)
    public void addUtente(Utente u){
        if(utenteRepository.existsByEmail(u.getEmail()))
            throw new IllegalArgumentException("utente gia esistente!");
        utenteRepository.save(u);
    }

    public static String getUserEmail(Principal user){
        String email=user.getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof OidcUser) email= ((OidcUser) auth.getPrincipal()).getEmail();
        System.out.println("email is"+ email);
        return  email;
    }

    @Transactional(readOnly = false)
    public Utente getUtente(Principal user){
        String email= getUserEmail(user);
        if(utenteRepository.existsByEmail(email))
            return getUtente(email);
        return accounting(user);
    }

    @Transactional(readOnly = true)
    public Utente getUtente(String email){
        return utenteRepository.findByEmail(email);
    }

    @Transactional
    public Utente accounting(Principal user){
        String email=user.getName();
        Utente u=new Utente();
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof OidcUser){
            email=((OidcUser) auth.getPrincipal()).getEmail();
            u.setNome(((OidcUser) auth.getPrincipal()).getFullName());
        }
        u.setCarrello(new LinkedList<>());
        u.setOrdini(new LinkedList<>());
        u.setEmail(email);
        utenteRepository.saveAndFlush(u);
        System.out.println(u);
        return u;
    }
}
