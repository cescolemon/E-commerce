package com.calabrianshop.progettopsw.reporsitories;

import com.calabrianshop.progettopsw.entities.ProdottoInCarrello;
import com.calabrianshop.progettopsw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoInCarrelloRepository extends JpaRepository<ProdottoInCarrello,Integer> {

    List<ProdottoInCarrello> findByUtente(Utente u);
    List<ProdottoInCarrello>  findByUtente_Id(int id);
    List<ProdottoInCarrello> findByUtente_Email(String email);
    ProdottoInCarrello findById(int id);

}
