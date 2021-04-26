package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Prodotto;
import com.calabrianshop.progettopsw.reporsitories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private EntityManager em;

    @Transactional(readOnly = true)
    public Prodotto getProdotto(int id){
        return prodottoRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public List<Prodotto> showAllProducts(){
        return prodottoRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void deleteProduct(int id){
        if(!prodottoRepository.existsById(id))return;
        prodottoRepository.deleteById(id);
        prodottoRepository.flush();
    }



    @Transactional(readOnly = false)
    public void addProduct(Prodotto prodotto){
        List<Prodotto> prodotti= prodottoRepository.findAll();
        for(Prodotto p : prodotti){
            if(p.getNome().equals(prodotto.getNome()) && p.getVenditore().equals(prodotto.getVenditore()))
                throw new IllegalArgumentException(("Prodotto già esistente!"));
        }
        prodottoRepository.save(prodotto);
    }

    @Transactional(readOnly = false)
    public void updateQuantita(int id){
        if(!prodottoRepository.existsById(id))
            throw  new IllegalArgumentException("prodotto inesistente!");
        Prodotto old=em.find(Prodotto.class, id);
        em.lock(old,LockModeType.OPTIMISTIC);
        Integer quantita= old.getQuantita();
        old.setQuantita(quantita+1);
        em.flush();
        em.lock(old,LockModeType.NONE);
    }

    @Transactional(readOnly = false)
    public void updateProduct(Prodotto prodotto){
        if(!prodottoRepository.existsById(prodotto.getId()))
            throw  new IllegalArgumentException("prodotto inesistente!");
        Prodotto old=em.find(Prodotto.class, prodotto.getId());
        em.lock(old,LockModeType.OPTIMISTIC);
        Double prezzo=prodotto.getPrezzo();
        Integer quantita=prodotto.getQuantita();
        String nome=prodotto.getNome();
        String venditore= prodotto.getVenditore();
        String categoria=prodotto.getCategoria();
        if(prezzo!=null) old.setPrezzo(prezzo);
        if(quantita!=null) old.setQuantita(quantita);
        if(nome!=null) old.setNome(nome);
        if(venditore!=null)old.setVenditore(venditore);
        if(categoria!=null)old.setCategoria(categoria);
        em.flush();
        em.lock(old,LockModeType.NONE);
    }

    @Transactional(readOnly = true)
    public int getQuantita(int id){
        Prodotto p= prodottoRepository.findById(id);
        if(p==null)return 0;
        return p.getQuantita();
    }

}
