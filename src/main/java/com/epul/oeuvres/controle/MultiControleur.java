package com.epul.oeuvres.controle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.utilitaires.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/***
 * Controller commun
 */
@Controller
public class MultiControleur {

	/***
	 * Return Error ModalAndView
	 * @return
	 */
	public ModelAndView errorPage() {
		return new ModelAndView(Constantes.ERROR_PAGE);
	}

	/***
	 * Return home view
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public ModelAndView Afficheindex(HttpServletRequest request, HttpServletResponse response) throws MonException {
		return AfficheRoot(request);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView AfficheRoot(HttpServletRequest request) throws MonException {
		return new ModelAndView("home");
	}
}
