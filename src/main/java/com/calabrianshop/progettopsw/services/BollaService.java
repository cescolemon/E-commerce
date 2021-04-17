package com.calabrianshop.progettopsw.services;

import com.calabrianshop.progettopsw.entities.Bolla;
import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.reporsitories.BollaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class BollaService {

   @Autowired
    private BollaRepository bollaRepository;

    @Autowired
    EntityManager entityManager;

    @Transactional(readOnly = false)
    public Bolla generaBolla(HttpServletRequest user, Ordine ordine){
        Bolla b= new Bolla();
        int id=ordine.getId();
        b.setId(id);
        b.setOrdine(ordine);
        return b;
    }

}
