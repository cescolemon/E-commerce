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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.TreeSet;

@RestController
@RequestMapping("/shop")
@CrossOrigin("http://localhost:4300")
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
    public ResponseEntity regOridne(@AuthenticationPrincipal HttpServletRequest user, @RequestParam("indirizzo") String indirizzo) {
        Ordine o = carrelloService.registraOrdine(user,indirizzo);
        return new ResponseEntity(o, HttpStatus.OK);
    }


    @GetMapping("/categorie")
    private TreeSet<String> getCategorie() {
        return categoriaService.getShowAllCategories();
    }


    @CrossOrigin("http://localhost:4300")
    @GetMapping("/cart")
    private Carrello getCarrello(@AuthenticationPrincipal HttpServletRequest user) {
        return utenteService.getCarrello(user);
    }

    @PostMapping("/updatecart")
    private ResponseEntity setCarrello(@AuthenticationPrincipal HttpServletRequest user, @RequestBody Carrello carrello) {
        List<ProdottoInCarrello> newCarrello = carrelloService.updateCarrello(user, carrello.getProdotti());
        if (newCarrello != null)
            return new ResponseEntity(newCarrello, HttpStatus.OK);
        return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);

    }
    @CrossOrigin("http://localhost:4300")
    @PostMapping("/addtocart")
    @ResponseBody
    private ResponseEntity addToCart(@AuthenticationPrincipal HttpServletRequest user, @RequestBody ProdottoInCarrello prodotto) {
        ProdottoInCarrello p = carrelloService.aggiungiProdotto(user, prodotto);
        return new ResponseEntity(p, HttpStatus.OK);
    }
}