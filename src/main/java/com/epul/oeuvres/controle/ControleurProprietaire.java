package com.epul.oeuvres.controle;

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
@RequestMapping("/proprietaires/")
public class ControleurProprietaire extends MultiControleur {

    ServiceProprietaire serviceProprietaire = new ServiceProprietaire();

    private static final String PROPRIETAIRE = "Proprietaire";
    private static final String LISTER_PROPRIETAIRE = "listerProprietaire.htm";
    private static final String AJOUTER_PROPRIETAIRE = "ajouterProprietaire";
    private static final String INSERER_PROPRIETAIRE = "insererProprietaire";
    private static final String DETAIL_PROPRIETAIRE = "detailProprietaire";
    private static final String UPDATE_PROPRIETAIRE = "updateProprietaire";
    private static final String DELETE_PROPRIETAIRE = "deleteProprietaire";

    @RequestMapping(value = LISTER_PROPRIETAIRE)
    public ModelAndView getProprietaireList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            request.setAttribute("mesProprietaires", serviceProprietaire.consulterProprietaires());
            destinationPage = "/proprietaire/listerProprietaire";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return new ModelAndView(destinationPage);
    }

    @RequestMapping("ajouterProprietaire.htm")
    public ModelAndView getProprietaireForm(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("actionSubmit", INSERER_PROPRIETAIRE);
        return new ModelAndView("proprietaire/formProprietaire");
    }

    /***
     * Insert and redirect to list
     * @param request
     * @return
     */
    @RequestMapping("insererProprietaire.htm")
    public ModelAndView insertProprietaire(HttpServletRequest request) {
        return errorPage();
    }
}
