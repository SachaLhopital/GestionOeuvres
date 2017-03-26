package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceAdherent;
import com.epul.oeuvres.dao.ServiceOeuvre;
import com.epul.oeuvres.dao.ServiceReservation;
import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Reservation;
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
@RequestMapping("/reservations/")
public class ControleurReservation extends MultiControleur {

    ServiceReservation serviceReservation = new ServiceReservation();
    ServiceAdherent serviceAdherent = new ServiceAdherent();
    ServiceOeuvre serviceOeuvre = new ServiceOeuvre();

    private static final String RESERVATION = "reservation";
    private static final String LISTER_RESERVATION = "listerReservation.htm";
    private static final String AJOUTER_RESERVATION = "ajouterReservation";
    private static final String INSERER_RESERVATION = "insererReservation";
    private static final String DELETE_RESERVATION = "deleteReservation";

    @RequestMapping(LISTER_RESERVATION)
    public ModelAndView getReservationList(HttpServletRequest request) throws MonException {
        try {
            request.setAttribute("mesReservations", new ServiceReservation().consulterReservations());
            return new ModelAndView("reservation/listerReservation");
        } catch (MonException e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return errorPage();
    }

    @RequestMapping(AJOUTER_RESERVATION + "/{adherentId}")
    public ModelAndView addReservation(
            @PathVariable("adherentId") int adherentId,
            HttpServletRequest request) throws MonException {

        if (adherentId == 0) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }

        try {
            request.setAttribute("adherent", serviceAdherent.consulterAdherent(adherentId));
            request.setAttribute("oeuvresvente", serviceOeuvre.GetListeOeuvreventeLibres());
            request.setAttribute("actionSubmit", "/reservations/" + INSERER_RESERVATION);
            return new ModelAndView("/reservation/formReservation");

        }catch(MonException e) {
            request.setAttribute(
                    Constantes.ERROR_KEY,
                    Constantes.ERROR_LISTING.replace("%s", "Formulaire Reservation"));
        }
        return errorPage();
    }

    @RequestMapping(INSERER_RESERVATION)
    public ModelAndView insertReservation (HttpServletRequest request) throws MonException {
        if(request.getParameter("idAdherent") == null || request.getParameter("txtOeuvre") == null) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
            return errorPage();
        }

        try {
            Reservation resa = new Reservation();

            resa.setAdherent(
                    serviceAdherent.consulterAdherent(
                            Integer.parseInt(request.getParameter("idAdherent"))));
            resa.setDate(new java.util.Date());
            resa.setOeuvrevente(
                    serviceOeuvre.consulterOeuvreVente(
                            Integer.parseInt(request.getParameter("txtOeuvre"))));

            serviceReservation.insererReservation(resa);
            return getReservationList(request);

        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_INSERT.replace("%s", RESERVATION));
        }
        return errorPage();
    }


    @RequestMapping(DELETE_RESERVATION + "/{date}/{id}")
    public ModelAndView removeReservation(
            @PathVariable("id") int id,
            @PathVariable("date") String date,
            HttpServletRequest request) throws MonException {
        if(id == 0 || date.equals("")) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
            return errorPage();
        }

        try {
            serviceReservation.supprimerReservation(serviceReservation.getReservationByDateAndOeuvre(date, id));
            return getReservationList(request);
        } catch(Exception e){
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
        }
        return errorPage();
    }
}
