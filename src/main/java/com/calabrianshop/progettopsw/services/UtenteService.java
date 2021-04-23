package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.reporsitories.UtenteRepository;
import com.calabrianshop.progettopsw.support.Carrello;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EntityManager entityManager;



    @Transactional(readOnly = false)
    public Carrello getCarrello( ){
        Utente u= getUtente();
        List<ProdottoInCarrello> prodotti = (List<ProdottoInCarrello>) u.getCarrello();
        return new Carrello(prodotti);
    }

    @Transactional(readOnly = false)
    public List<Utente> showAllUtente(){
        return utenteRepository.findAll();
    }

    @Transactional(readOnly = false)
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
    public Utente addUtente(Utente u){
        if(utenteRepository.existsByEmail(u.getEmail()))
            throw new IllegalArgumentException("utente gia esistente!");
        return utenteRepository.save(u);
    }
    @ResponseBody
    public  String getUserEmail(){
        String email = getTokenNode().get("claims").get("email").asText();
        System.out.println("email is"+ email);
        return  email;
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
    public Utente getUtente(){
        String email= getUserEmail();
        if(utenteRepository.existsByEmail(email))
            return getUtente(email);
        return accounting();
    }

    @Transactional(readOnly = false)
    public Utente getUtente(String email){
        return utenteRepository.findByEmail(email);
    }

    @Transactional
    public Utente accounting(){
        String email = getTokenNode().get("claims").get("email").asText();
        Utente u=new Utente();
        u.setEmail(email);
        u.setNome(getTokenNode().get("claims").get("name").asText());
        u.setCarrello(new LinkedList<>());
        u.setOrdini(new LinkedList<>());
        utenteRepository.saveAndFlush(u);
        System.out.println(u);
        return u;
    }

    @Transactional(readOnly = true)
    public List<Ordine> showAllOrdini( ){
        Utente u= accounting();
        Collection<Ordine> ordines= u.getOrdini();
        List<Ordine> ret= (List<Ordine>)u.getOrdini();
        return ret;
    }
}
