package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.Service;
import com.epul.oeuvres.dao.ServiceAdherent;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Adherent;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sachouw on 11/03/2017.
 */
@Controller
public class ControleurAdherent {

    public static final String ADHERENT = "adhérent";
    private static final String LISTER_ADHERENT = "listerAdherent";
    private static final String AJOUTER_ADHERENT = "ajouterAdherent";
    private static final String INSERER_ADHERENT = "insererAdherent.htm";
    private static final String DETAIL_ADHERENT = "detailAdherent";
    private static final String UPDATE_ADHERENT = "updateAdherent.htm";
    private static final String SUPPRIMER_ADHERENT = "supprimerAdherent.htm";

    /***
     * Get Adherent List
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "listerAdherent.htm")
    public ModelAndView getAdherentList(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("mesAdherents", new ServiceAdherent().consulterListeAdherents());
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
    @RequestMapping("ajouterAdherent.htm")
    public ModelAndView getAdherentForm(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("actionSubmit", INSERER_ADHERENT);
        return new ModelAndView("adherent/formAdherent");
    }

    /***
     * Insert and redirect to list
     * @param request
     * @return
     */
    @RequestMapping("insererAdherent.htm")
    public ModelAndView insertAdherent(HttpServletRequest request) {
        try {
            Adherent unAdherent = new Adherent();
            unAdherent.setNomAdherent(request.getParameter("txtnom"));
            unAdherent.setPrenomAdherent(request.getParameter("txtprenom"));
            unAdherent.setVilleAdherent(request.getParameter("txtville"));

            new ServiceAdherent().insertAdherent(unAdherent);
            return getAdherentList(request, null);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_INSERT.replace("%s", ADHERENT));
        }
        return errorPage();
    }

    /***
     * Get an Adherent by Id and redirect to form
     * @param request
     * @return
     */
    @RequestMapping("detailAdherent.htm")
    public ModelAndView getAdherent(HttpServletRequest request) {
        try {
            if (request.getParameter("id").isEmpty() || request.getParameter("id") == null) {
                request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                return errorPage();
            }

            Adherent a = new ServiceAdherent().consulterAdherent(Integer.parseInt(request.getParameter("id")));

            if (a == null) {
                request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL.replace("%s", ADHERENT));
                return errorPage();
            }

            request.setAttribute("adherent", a);
            request.setAttribute("actionSubmit", UPDATE_ADHERENT);
            return new ModelAndView("adherent/formAdherent");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL.replace("%s", ADHERENT));
        }
        return errorPage();
    }

    /***
     * Update Adherent and redirect to List
     * @param request
     * @return
     */
    @RequestMapping(UPDATE_ADHERENT)
    public ModelAndView updateAdherent(HttpServletRequest request) {
        if(request.getParameter("txtId") == null
                || request.getParameter("txtnom").isEmpty()
                || request.getParameter("txtprenom").isEmpty()
                || request.getParameter("txtville").isEmpty()) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }

        try {
            ServiceAdherent service = new ServiceAdherent();
            Adherent adherentToUpdate = service
                    .consulterAdherent(Integer.parseInt(request.getParameter("txtId")));
            adherentToUpdate.setNomAdherent(request.getParameter("txtnom"));
            adherentToUpdate.setPrenomAdherent(request.getParameter("txtprenom"));
            adherentToUpdate.setVilleAdherent(request.getParameter("txtville"));
            service.modifierAdherent(adherentToUpdate);
            return getAdherentList(request, null);

        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_UPDATING);
        }
        return errorPage();
    }

    /***
     * Delete an Adherent and redirect to list
     * @param request
     * @return
     */
    @RequestMapping(SUPPRIMER_ADHERENT)
    public ModelAndView deleteAdherent(HttpServletRequest request) {
        if(request.getParameter("id") == null) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }
        try {
            ServiceAdherent service = new ServiceAdherent();
            service.supprimerAdherent(service.consulterAdherent(Integer.parseInt(request.getParameter("id"))));
            return getAdherentList(request, null);
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
        }
        return errorPage();
    }

    /***
     * Return Error ModalAndView
     * @return
     */
    private ModelAndView errorPage() {
        return new ModelAndView(Constantes.ERROR_PAGE);
    }
}