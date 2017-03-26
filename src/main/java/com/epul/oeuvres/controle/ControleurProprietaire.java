package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceProprietaire;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Proprietaire;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final String AJOUTER_PROPRIETAIRE = "ajouterProprietaire.htm";
    private static final String INSERER_PROPRIETAIRE = "insererProprietaire.htm";
    private static final String DETAIL_PROPRIETAIRE = "detailProprietaire";
    private static final String UPDATE_PROPRIETAIRE = "updateProprietaire";
    private static final String DELETE_PROPRIETAIRE = "deleteProprietaire";

    @RequestMapping(value = LISTER_PROPRIETAIRE)
    public ModelAndView getProprietaireList(HttpServletRequest request) throws MonException {
        try {
            request.setAttribute("mesProprietaires", serviceProprietaire.consulterProprietaires());
            return new ModelAndView("/proprietaire/listerProprietaire");
        } catch (MonException e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return errorPage();
    }

    @RequestMapping(AJOUTER_PROPRIETAIRE)
    public ModelAndView getProprietaireForm(HttpServletRequest request, HttpServletResponse response) throws MonException {
        request.setAttribute("actionSubmit", "/proprietaires/" + INSERER_PROPRIETAIRE);
        return new ModelAndView("/proprietaire/formProprietaire");
    }

    /***
     * Insert and redirect to list
     * @param request
     * @return
     */
    @RequestMapping(INSERER_PROPRIETAIRE)
    public ModelAndView insertProprietaire(HttpServletRequest request) throws MonException {
        try {
            Proprietaire unProprietaire = new Proprietaire();
            unProprietaire.setNomProprietaire(request.getParameter("txtnom"));
            unProprietaire.setPrenomProprietaire(request.getParameter("txtprenom"));

            serviceProprietaire.insererProprietaire(unProprietaire);
            return getProprietaireList(request);

        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_INSERT);
        }
        return errorPage();
    }

    @RequestMapping(DETAIL_PROPRIETAIRE + "/{id}")
    public ModelAndView getDetailsProprietaire(@PathVariable("id") int id, HttpServletRequest request) throws MonException {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }

        Proprietaire p = serviceProprietaire.consulterProprietaire(id);

        if(p == null) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL);
            return errorPage();
        }

        request.setAttribute("proprietaire", p);
        request.setAttribute("actionSubmit", "/proprietaires/" + UPDATE_PROPRIETAIRE);
        return new ModelAndView("/proprietaire/formProprietaire");
    }

    @RequestMapping(UPDATE_PROPRIETAIRE)
    public ModelAndView updateProprietaire(HttpServletRequest request) throws MonException {
        if(request.getParameter("txtId") == null || request.getParameter("txtnom").isEmpty()) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }

        Proprietaire proprietaireToUpdate = serviceProprietaire
                .consulterProprietaire(Integer.parseInt(request.getParameter("txtId")));
        proprietaireToUpdate.setNomProprietaire(request.getParameter("txtnom"));
        proprietaireToUpdate.setPrenomProprietaire(request.getParameter("txtprenom"));
        serviceProprietaire.modifierProprietaire(proprietaireToUpdate);
        return getProprietaireList(request);
    }

    @RequestMapping(DELETE_PROPRIETAIRE + "/{id}")
    public ModelAndView removeProprietaire(@PathVariable("id") int id, HttpServletRequest request) throws MonException {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }
        try {
            serviceProprietaire.supprimerProprietaire(serviceProprietaire.consulterProprietaire(id));
            return getProprietaireList(request);
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
        }
        return errorPage();
    }
}
