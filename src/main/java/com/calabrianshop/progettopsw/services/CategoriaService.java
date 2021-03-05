package com.calabrianshop.progettopsw.services;


import com.calabrianshop.progettopsw.entities.Prodotto;
import com.calabrianshop.progettopsw.reporsitories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeSet;

@Service
public class CategoriaService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Transactional(readOnly = true)
    public TreeSet<String> getShowAllCategories() {
        List<Prodotto> e = prodottoRepository.findAll();
        TreeSet<String> ret= new TreeSet<String>();
        for(Prodotto p: e){
            ret.add(p.getCategoria());
        }
        return ret;
    }
}
