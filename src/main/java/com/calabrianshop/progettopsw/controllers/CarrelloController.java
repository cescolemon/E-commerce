package com.calabrianshop.progettopsw.controllers;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;
import com.calabrianshop.progettopsw.entities.Utente;
import com.calabrianshop.progettopsw.services.CarrelloService;
import com.calabrianshop.progettopsw.services.UtenteService;
import com.calabrianshop.progettopsw.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/cart")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;
    @Autowired
    private UtenteService utenteService;


    @PostMapping(value = "/orderreg",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity regOridne( @RequestBody String indirizzo) {
        System.out.println(indirizzo);
        Ordine o = carrelloService.registraOrdine(indirizzo);
        return new ResponseEntity(o, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public Carrello getProdottiInCarr() {
        Utente u = utenteService.getUtente();
        Carrello carr= new Carrello(carrelloService.getProdottiCarrello(u.getEmail()));
        return carr;
    }

    @GetMapping("/empty")
    @ResponseBody
    public ResponseEntity emptyCarrello(){
        carrelloService.emptyCart();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity rimuoviProdotto( @RequestBody ProdottoInCarrello prodottoInCarrello){
        carrelloService.rimuoviProdottoInCarrello( prodottoInCarrello);
        return new ResponseEntity(prodottoInCarrello, HttpStatus.OK);
    }


}