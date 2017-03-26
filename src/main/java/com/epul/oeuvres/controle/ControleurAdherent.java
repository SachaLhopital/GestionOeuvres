package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceAdherent;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Adherent;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sachouw on 11/03/2017.
 */
@Controller
@RequestMapping("/adherents/")
public class ControleurAdherent extends MultiControleur {

    private ServiceAdherent service = new ServiceAdherent();

    public static final String ADHERENT = "adhérent";
    private static final String LISTER_ADHERENT = "listerAdherent.htm";
    private static final String AJOUTER_ADHERENT = "ajouterAdherent.htm";
    private static final String INSERER_ADHERENT = "insererAdherent.htm";
    private static final String UPDATE_ADHERENT = "updateAdherent.htm";

    @RequestMapping(value = LISTER_ADHERENT)
    public ModelAndView getAdherentList(HttpServletRequest request) {
        try {
            request.setAttribute("mesAdherents", service.consulterListeAdherents());
            return new ModelAndView( "adherent/listerAdherent");
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return errorPage();
    }

    /***
     * Redirect to Adherent's form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(AJOUTER_ADHERENT)
    public ModelAndView getAdherentForm(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("actionSubmit", INSERER_ADHERENT);
        return new ModelAndView("adherent/formAdherent");
    }

    /***
     * Insert and redirect to list
     * @param request
     * @return
     */
    @RequestMapping(INSERER_ADHERENT)
    public ModelAndView insertAdherent(HttpServletRequest request) {
        try {
            Adherent unAdherent = new Adherent();
            unAdherent.setNomAdherent(request.getParameter("txtnom"));
            unAdherent.setPrenomAdherent(request.getParameter("txtprenom"));
            unAdherent.setVilleAdherent(request.getParameter("txtville"));

            service.insertAdherent(unAdherent);
            return getAdherentList(request);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_INSERT.replace("%s", ADHERENT));
        }
        return errorPage();
    }

    @RequestMapping("detailAdherent/{id}")
    public ModelAndView getAdherent(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            if (id == 0) {
                request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                return errorPage();
            }

            Adherent a = service.consulterAdherent(id);

            if (a == null) {
                request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL.replace("%s", ADHERENT));
                return errorPage();
            }

            request.setAttribute("adherent", a);
            request.setAttribute("actionSubmit", "/adherents/" + UPDATE_ADHERENT);
            return new ModelAndView("adherent/formAdherent");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL.replace("%s", ADHERENT));
        }
        return errorPage();
    }

    @RequestMapping(UPDATE_ADHERENT)
    public ModelAndView updateAdherent(HttpServletRequest request) throws MonException {
        if(request.getParameter("txtId") == null
                || request.getParameter("txtnom").isEmpty()
                || request.getParameter("txtprenom").isEmpty()
                || request.getParameter("txtville").isEmpty()) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }

        try {
            Adherent adherentToUpdate = service
                    .consulterAdherent(Integer.parseInt(request.getParameter("txtId")));
            adherentToUpdate.setNomAdherent(request.getParameter("txtnom"));
            adherentToUpdate.setPrenomAdherent(request.getParameter("txtprenom"));
            adherentToUpdate.setVilleAdherent(request.getParameter("txtville"));
            service.modifierAdherent(adherentToUpdate);
            return getAdherentList(request);

        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_UPDATING);
        }
        return errorPage();
    }

    @RequestMapping("supprimerAdherent/{id}")
    public ModelAndView deleteAdherent(@PathVariable("id") int id, HttpServletRequest request) {
        if(id == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }
        try {
            service.supprimerAdherent(service.consulterAdherent(id));
            return getAdherentList(request);
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
        }
        return errorPage();
    }
}
