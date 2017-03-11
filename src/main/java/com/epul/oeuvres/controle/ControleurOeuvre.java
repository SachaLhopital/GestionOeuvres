package com.epul.oeuvres.controle;

import com.epul.oeuvres.dao.ServiceAdherent;
import com.epul.oeuvres.dao.ServiceOeuvre;
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
public class ControleurOeuvre {

    private static final String OEUVRE = "Oeuvre";
    private static final String PRET = "pret";
    private static final String VENTE = "vente";

    @RequestMapping("listerOeuvre.htm")
    public ModelAndView getOeuvresList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServiceOeuvre unService = new ServiceOeuvre();
        String destinationPage = Constantes.ERROR_PAGE;

        try {
            request.setAttribute("mesOeuvresPret", unService.ConsulterListeOeuvrepret());
            request.setAttribute("mesOeuvresVente", unService.ConsulterListeOeuvrevente());
            destinationPage = "oeuvre/listerOeuvre";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING.replace("%s", OEUVRE));
        }
        return new ModelAndView(destinationPage);
    }
}
