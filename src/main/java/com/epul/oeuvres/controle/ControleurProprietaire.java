package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceAdherent;
import com.epul.oeuvres.dao.ServiceProprietaire;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sachouw on 11/03/2017.
 */
@Controller
public class ControleurProprietaire {

    private static final String LISTER_PROPRIETAIRE = "listerProprietaire";
    private static final String AJOUTER_PROPRIETAIRE = "ajouterProprietaire";
    private static final String INSERER_PROPRIETAIRE = "insererProprietaire";
    private static final String DETAIL_PROPRIETAIRE = "detailProprietaire";
    private static final String UPDATE_PROPRIETAIRE = "updateProprietaire";
    private static final String DELETE_PROPRIETAIRE = "deleteProprietaire";

    @RequestMapping(value = "listerProprietaire.htm")
    public ModelAndView getProprietaireList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServiceProprietaire serviceProprietaire = new ServiceProprietaire();
        String destinationPage = Constantes.ERROR_PAGE;

        try {
            request.setAttribute("mesProprietaires", serviceProprietaire.consulterProprietaires());
            destinationPage = "proprietaire/listerProprietaire";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }

        return new ModelAndView(destinationPage);
    }
}
