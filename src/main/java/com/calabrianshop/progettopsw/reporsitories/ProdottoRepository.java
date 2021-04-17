package com.calabrianshop.progettopsw.reporsitories;

import com.calabrianshop.progettopsw.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto,Integer> {

    Prodotto findById(int id);
    Prodotto findByNome(String nome);
    List<Prodotto> findByPrezzoGreaterThan(double prezzo);
    List<Prodotto> findByPrezzoLessThan(double prezzo);
    List<Prodotto> findByVenditore(String venditore);

    boolean existsById(int id);
    boolean existsByNome(String nome);
    List<Prodotto> findByCategoria(String categoria);
    void deleteById (int id);
}
