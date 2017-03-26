package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceOeuvre;
import com.epul.oeuvres.dao.ServiceProprietaire;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Oeuvrepret;
import com.epul.oeuvres.metier.Oeuvrevente;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
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

    private ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
    private ServiceProprietaire serviceProprietaire = new ServiceProprietaire();

    private static final String OEUVRE = "Oeuvre";
    private static final String PRET = "pret";
    private static final String VENTE = "vente";
    private static final String LISTER_OEUVRES = "listerOeuvre.htm";
    private static final String AJOUTER_OEUVRES = "ajouterOeuvre";
    private static final String INSERER_OEUVRES = "insererOeuvre";
    private static final String UPDATE_OEUVRES = "updateOeuvre";

    /******************************
     *  COMMUN
     *****************************/

    @RequestMapping(LISTER_OEUVRES)
    public ModelAndView getOeuvresList(HttpServletRequest request) throws MonException {

        try {
            request.setAttribute("mesOeuvresPret", serviceOeuvre.ConsulterListeOeuvrepret());
            request.setAttribute("mesOeuvresVente", serviceOeuvre.ConsulterListeOeuvrevente());
            return new ModelAndView("oeuvre/listerOeuvre");
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING.replace("%s", OEUVRE));
        }
        return errorPage();
    }

    @RequestMapping(AJOUTER_OEUVRES + "/{type}/{id}")
    public ModelAndView addOeuvre(
            @PathVariable("id") int id,
            @PathVariable("type") String type,
            HttpServletRequest request) throws Exception {

        if (type.equals("") || id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }
        request.setAttribute("type", type);
        request.setAttribute("actionSubmit", "/oeuvres/insererOeuvre");
        request.setAttribute("proprietaire", serviceProprietaire.consulterProprietaire(id));
        return new ModelAndView("/oeuvre/formOeuvre");
    }

    @RequestMapping(INSERER_OEUVRES)
    public ModelAndView insertOeuvre(HttpServletRequest request) throws MonException {
        if(request.getParameter("type").isEmpty()
                || request.getParameter("type") == null
                || request.getParameter("propId").isEmpty()
                || request.getParameter("propId") == null) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }
        try {
            if(PRET.equals(request.getParameter("type"))) {
                Oeuvrepret oeuvre = new Oeuvrepret();

                oeuvre.setTitreOeuvrepret(request.getParameter("txttitre"));
                oeuvre.setProprietaire(
                        serviceProprietaire.consulterProprietaire(Integer.parseInt(request.getParameter("propId"))));
                serviceOeuvre.insertOeuvrepret(oeuvre);
            }

            if(VENTE.equals(request.getParameter("type"))) {
                Oeuvrevente oeuvre = new Oeuvrevente();

                oeuvre.setTitreOeuvrevente(request.getParameter("txttitre"));
                oeuvre.setEtatOeuvrevente(Constantes.EtatsOeuvre.L.toString());
                oeuvre.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtprix")));
                oeuvre.setProprietaire(
                        serviceProprietaire.consulterProprietaire(Integer.parseInt(request.getParameter("propId"))));
                serviceOeuvre.insertOeuvrevente(oeuvre);
            }

        } catch (MonException ex) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_INSERT.replace("%s", OEUVRE + request.getParameter("type")));
            return errorPage();
        }
        return getOeuvresList(request);
    }

    @RequestMapping(UPDATE_OEUVRES)
    public ModelAndView updateOeuvre(HttpServletRequest request) throws MonException {

        if(request.getParameter("type") == null
                || request.getParameter("propId") == null
                || request.getParameter("oeuvreId") == null) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }

        try {
            if(PRET.equals(request.getParameter("type"))) {

                Oeuvrepret oeuvreToUpdate = serviceOeuvre
                        .consulterOeuvrePret(Integer.parseInt(request.getParameter("oeuvreId")));

                oeuvreToUpdate.setProprietaire(
                        serviceProprietaire.consulterProprietaire(
                                Integer.parseInt(request.getParameter("propId"))));
                oeuvreToUpdate.setTitreOeuvrepret(request.getParameter("txttitre"));

                serviceOeuvre.modifierOeuvrepret(oeuvreToUpdate);
            }

            if(VENTE.equals(request.getParameter("type"))) {
                Oeuvrevente oeuvreToUpdate = serviceOeuvre
                        .consulterOeuvreVente(Integer.parseInt(request.getParameter("oeuvreId")));

                oeuvreToUpdate.setProprietaire(
                        serviceProprietaire.consulterProprietaire(
                                Integer.parseInt(request.getParameter("propId"))));
                oeuvreToUpdate.setTitreOeuvrevente(request.getParameter("txttitre"));
                oeuvreToUpdate.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtprix")));

                serviceOeuvre.modifierOeuvrevente(oeuvreToUpdate);
            }

        } catch(MonException e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_UPDATING);
            errorPage();
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
            request.setAttribute("actionSubmit", "/oeuvres/" + UPDATE_OEUVRES);
            return new ModelAndView("/oeuvre/formOeuvre");

        } catch(MonException e) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_DETAIL.replace("%s", OEUVRE + PRET));
        }
        return errorPage();
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
            errorPage();
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

            request.setAttribute("type", VENTE);
            request.setAttribute("oeuvre", o);
            request.setAttribute("proprietaire", o.getProprietaire());
            request.setAttribute("actionSubmit", "/oeuvres/updateOeuvre");
            return new ModelAndView("/oeuvre/formOeuvre");

        } catch(MonException e) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_DETAIL.replace("%s", OEUVRE + VENTE));
        }
        return errorPage();
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
            errorPage();
        }
        return getOeuvresList(request);
    }
}
