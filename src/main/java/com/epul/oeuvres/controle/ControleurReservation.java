package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceReservation;
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
@RequestMapping("/reservations/")
public class ControleurReservation {

    @RequestMapping("listerReservation.htm")
    public ModelAndView getReservationList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String destinationPage = Constantes.ERROR_PAGE;

        try {
            request.setAttribute("mesReservations", new ServiceReservation().consulterReservations());
            destinationPage = "reservation/listerReservation";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return new ModelAndView(destinationPage);
    }
}
