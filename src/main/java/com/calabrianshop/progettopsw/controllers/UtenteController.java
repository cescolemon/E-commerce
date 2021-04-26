package com.calabrianshop.progettopsw.controllers;


import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UtenteController {


    @Autowired
    private UtenteService utenteService;


    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping
    public List<Utente> allUtente(){
        return utenteService.showAllUtente();}



    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping("/add")
    public void addUtente(@RequestBody Utente u){
        System.out.println(u);
        utenteService.addUtente(u);
    }


    @CrossOrigin(origins = {"http://localhost:4200"})
    @DeleteMapping(path = "/{id}")
    public void deleteUtente(@PathVariable("id") int id){
        utenteService.deleteUtentebyId(id);
    }


}
