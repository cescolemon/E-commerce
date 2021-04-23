package com.calabrianshop.progettopsw.controllers;

import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.Prodotto;
import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;
import com.calabrianshop.progettopsw.services.CarrelloService;
import com.calabrianshop.progettopsw.services.CategoriaService;
import com.calabrianshop.progettopsw.services.ProdottoService;
import com.calabrianshop.progettopsw.services.UtenteService;
import com.calabrianshop.progettopsw.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeSet;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ProdottoService prodottoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private CarrelloService carrelloService;

    @GetMapping
    public List<Prodotto> getProdotti() {
        return prodottoService.showAllProducts();
    }

    @PostMapping("/orderreg")
    @ResponseBody
    public ResponseEntity regOridne( @RequestParam("indirizzo") String indirizzo) {
        Ordine o = carrelloService.registraOrdine(indirizzo);
        return new ResponseEntity(o, HttpStatus.OK);
    }


    @GetMapping("/categorie")
    private TreeSet<String> getCategorie() {
        return categoriaService.getShowAllCategories();
    }



    @GetMapping("/cart")
    private Carrello getCarrello() {
        return utenteService.getCarrello();
    }

    @PostMapping("/updatecart")
    private ResponseEntity setCarrello( @RequestBody Carrello carrello) {
        List<ProdottoInCarrello> newCarrello = carrelloService.updateCarrello( carrello.getProdotti());
        if (newCarrello != null)
            return new ResponseEntity(newCarrello, HttpStatus.OK);
        return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/addtocart")
    @ResponseBody
    private ResponseEntity addToCart( @RequestBody ProdottoInCarrello prodotto) {
        ProdottoInCarrello p = carrelloService.aggiungiProdotto( prodotto);
        return new ResponseEntity(p, HttpStatus.OK);
    }
}