package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceOeuvre;
import com.epul.oeuvres.dao.ServiceProprietaire;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Oeuvrepret;
import com.epul.oeuvres.metier.Oeuvrevente;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sachouw on 11/03/2017.
 */
@Controller
@RequestMapping("/oeuvres/")
public class ControleurOeuvre extends MultiControleur {

    ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
    ServiceProprietaire serviceProprietaire = new ServiceProprietaire();

    private static final String OEUVRE = "Oeuvre";
    private static final String PRET = "pret";
    private static final String VENTE = "vente";
    private static final String LISTER_OEUVRES = "listerOeuvre.htm";
    private static final String AJOUTER_OEUVRES = "ajouterOeuvre";
    private static final String INSERER_OEUVRES = "insererOeuvre";
    private static final String UPDATE_OEUVRES = "updateOeuvre";
    private static final String DELETE_OEUVRES = "deleteOeuvre";

    /******************************
     *  COMMUN
     *****************************/

    @RequestMapping(LISTER_OEUVRES)
    public ModelAndView getOeuvresList(HttpServletRequest request) throws MonException {

        try {
            request.setAttribute("mesOeuvresPret", serviceOeuvre.ConsulterListeOeuvrepret());
            request.setAttribute("mesOeuvresVente", serviceOeuvre.ConsulterListeOeuvrevente());
            destinationPage = "oeuvre/listerOeuvre";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING.replace("%s", OEUVRE));
        }
        return new ModelAndView(destinationPage);
    }

    @RequestMapping(AJOUTER_OEUVRES + "/{type}/{id}")
    public ModelAndView addOeuvre(@PathVariable("id") int id, @PathVariable("type") String type, HttpServletRequest request) throws Exception {
        if (type.equals("") || id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }
        request.setAttribute("enumValues", Constantes.EtatsOeuvre.values());
        request.setAttribute("type", type);
        request.setAttribute("actionSubmit", "/oeuvres/insererOeuvre");
        request.setAttribute("proprietaire", serviceProprietaire.consulterProprietaire(id));
        return new ModelAndView("/oeuvre/formOeuvre");
    }

    @RequestMapping(INSERER_OEUVRES + "/{type}/{proprietaireId}")
    public ModelAndView insertOeuvre(@PathVariable("proprietaireId") int proprietaireId, @PathVariable("type") String type, HttpServletRequest request) throws Exception {
        if(type.equals("") || proprietaireId == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }
        try {
            if(PRET.equals(type)) {
                Oeuvrepret oeuvre = new Oeuvrepret();

                oeuvre.setTitreOeuvrepret(request.getParameter("txttitre"));
                oeuvre.setProprietaire(serviceProprietaire.consulterProprietaire(proprietaireId));
                serviceOeuvre.insertOeuvrepret(oeuvre);
            }

            if(VENTE.equals(type)) {
                Oeuvrevente oeuvre = new Oeuvrevente();

                oeuvre.setTitreOeuvrevente(request.getParameter("txttitre"));
                oeuvre.setEtatOeuvrevente(request.getParameter("txtetat"));
                oeuvre.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtprix")));
                oeuvre.setProprietaire(serviceProprietaire.consulterProprietaire(proprietaireId));
                serviceOeuvre.insertOeuvrevente(oeuvre);
            }

        } catch (Exception e) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_INSERT.replace("%s", OEUVRE + request.getParameter("type")));
        }
        return getOeuvresList(request);
    }

    @RequestMapping(UPDATE_OEUVRES + "/{type}/{proprietaireId}/{oeuvreId}")
    public ModelAndView updateOeuvre(
            @PathVariable("proprietaireId") int proprietaireId,
            @PathVariable("oeuvreId") int oeuvreId,
            @PathVariable("type") String type,
            HttpServletRequest request) throws MonException {

        if(type.equals("") || proprietaireId == 0 || oeuvreId == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }

        try {
            if(PRET.equals(type)) {

                Oeuvrepret oeuvreToUpdate = serviceOeuvre.consulterOeuvrePret(oeuvreId);

                oeuvreToUpdate.setProprietaire(
                        serviceProprietaire.consulterProprietaire(proprietaireId));
                oeuvreToUpdate.setTitreOeuvrepret(request.getParameter("txttitre"));

                serviceOeuvre.modifierOeuvrepret(oeuvreToUpdate);
            }

            if(VENTE.equals(type)) {
                Oeuvrevente oeuvreToUpdate = serviceOeuvre.consulterOeuvreVente(oeuvreId);

                oeuvreToUpdate.setProprietaire(
                        serviceProprietaire.consulterProprietaire(proprietaireId));
                oeuvreToUpdate.setTitreOeuvrevente(request.getParameter("txttitre"));
                oeuvreToUpdate.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtprix")));
                oeuvreToUpdate.setEtatOeuvrevente(request.getParameter("txtetat"));

                serviceOeuvre.modifierOeuvrevente(oeuvreToUpdate);
            }
        } catch(MonException e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_UPDATING);
        }
        return getOeuvresList(request);
    }
    /******************************
     *  PRETS
     *****************************/

    @RequestMapping("detailOeuvrePret/{id}")
    public ModelAndView getDetailOeuvrePret(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }

        try {
            Oeuvrepret o = serviceOeuvre.consulterOeuvrePret(id);

            request.setAttribute("type", PRET);
            request.setAttribute("oeuvre", o);
            request.setAttribute("proprietaire", o.getProprietaire());
            request.setAttribute("actionSubmit", "/oeuvres/updateOeuvre");
            destinationPage = "/oeuvre/formOeuvre";

        } catch(MonException e) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_DETAIL.replace("%s", OEUVRE + PRET));
        }
        return new ModelAndView(destinationPage);
    }

    @RequestMapping("deleteOeuvrePret/{id}")
    public ModelAndView removeOeuvrePret(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }
        try {
            serviceOeuvre.supprimerOeuvrepret(serviceOeuvre.consulterOeuvrePret(id));
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
        }
        return getOeuvresList(request);
    }

    /******************************
     *  VENTE
     *****************************/

    @RequestMapping("detailOeuvreVente/{id}")
    public ModelAndView getDetailOeuvreVente(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }

        try {
            Oeuvrevente o = serviceOeuvre.consulterOeuvreVente(id);

            request.setAttribute("enumValues", Constantes.EtatsOeuvre.values());
            request.setAttribute("type", VENTE);
            request.setAttribute("oeuvre", o);
            request.setAttribute("proprietaire", o.getProprietaire());
            request.setAttribute("actionSubmit", "/oeuvres/updateOeuvre");
            destinationPage = "/oeuvre/formOeuvre";

        } catch(MonException e) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_DETAIL.replace("%s", OEUVRE + VENTE));
        }
        return new ModelAndView(destinationPage);
    }

    @RequestMapping("deleteOeuvreVente/{id}")
    public ModelAndView removeOeuvreVente(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }
        try {
            serviceOeuvre.supprimerOeuvrevente(
                    serviceOeuvre.consulterOeuvreVente(id));
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
        }
        return getOeuvresList(request);
    }
}
