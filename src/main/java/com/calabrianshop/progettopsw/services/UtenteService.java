package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.reporsitories.UtenteRepository;
import com.calabrianshop.progettopsw.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EntityManager entityManager;



    @Transactional(readOnly = true)
    public Carrello getCarrello( HttpServletRequest user){
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

    @Transactional(propagation = Propagation.REQUIRED)
    public Utente addUtente(Utente u){
        if(utenteRepository.existsByEmail(u.getEmail()))
            throw new IllegalArgumentException("utente gia esistente!");
        return utenteRepository.save(u);
    }
    @ResponseBody
    public static String getUserEmail(HttpServletRequest user){
        String email=user.getUserPrincipal().getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof OidcUser) email= ((OidcUser) auth.getPrincipal()).getEmail();
        System.out.println("email is"+ email);
        return  email;
    }

    @Transactional(readOnly = false)
    public Utente getUtente( HttpServletRequest user){
        String email= getUserEmail(user);
        if(utenteRepository.existsByEmail(email))
            return getUtente(email);
        return accounting(user);
    }

    @Transactional(readOnly = false)
    public Utente getUtente(String email){
        return utenteRepository.findByEmail(email);
    }

    @Transactional
    public Utente accounting( HttpServletRequest user){
        String email=user.getUserPrincipal().getName();
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

    @Transactional(readOnly = true)
    public List<Ordine> showAllOrdini( HttpServletRequest user){
        Utente u= accounting(user);
        Collection<Ordine> ordines= u.getOrdini();
        List<Ordine> ret= (List<Ordine>)u.getOrdini();
        return ret;
    }
}
