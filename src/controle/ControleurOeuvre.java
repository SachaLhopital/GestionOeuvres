package controle;

import dao.ServiceOeuvre;
import dao.ServiceProprietaire;
import meserreurs.MonException;
import metier.Oeuvrepret;
import metier.Oeuvrevente;
import metier.Proprietaire;
import utilitaires.Constantes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sachouw on 21/02/2017.
 */
@WebServlet("/ControleurOeuvre")
public class ControleurOeuvre extends HttpServlet {

    private static final String OEUVRE = "Oeuvre";
    private static final String PRET = "pret";
    private static final String VENTE = "vente";
    private static final String LISTER_OEUVRES = "listerOeuvre";
    private static final String AJOUTER_OEUVRES = "ajouterOeuvre";
    private static final String INSERER_OEUVRES = "insererOeuvre";
    private static final String UPDATE_OEUVRES = "updateOeuvre";
    private static final String DELETE_OEUVRES_PRET = "deleteOeuvrePret";
    private static final String DELETE_OEUVRES_VENTE = "deleteOeuvreVente";
    private static final String DETAIL_OEUVRE_PRET = "detailOeuvrePret";
    private static final String DETAIL_OEUVRE_VENTE = "detailOeuvreVente";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        processusTraiteRequete(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        processusTraiteRequete(request, response);
    }

    /***
     * Redirige vers la bonne vue, avec les bonnes données
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processusTraiteRequete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
        ServiceProprietaire serviceProprietaire = new ServiceProprietaire();
        String actionName = request.getParameter(Constantes.ACTION_TYPE);
        String destinationPage = Constantes.ERROR_PAGE;

        switch (actionName) {
            case LISTER_OEUVRES:
                destinationPage = setOeuvreListDestination(request);
                break;

            case AJOUTER_OEUVRES:
                if(request.getParameter("type") == null || request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
                    break;
                }
                request.setAttribute("enumValues", Constantes.EtatsOeuvre.values());
                request.setAttribute("type", request.getParameter("type"));
                request.setAttribute("actionSubmit","ControleurOeuvre?action=insererOeuvre");
                request.setAttribute(
                        "proprietaire",
                        serviceProprietaire.consulterProprietaire(Integer.parseInt(request.getParameter("id"))));
                destinationPage = "/content/oeuvre/formOeuvre.jsp";
                break;

            case INSERER_OEUVRES:
                if(request.getParameter("type") == null || request.getParameter("propId") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
                    break;
                }
                try {
                    if(PRET.equals(request.getParameter("type"))) {
                        Oeuvrepret oeuvre = new Oeuvrepret();

                        oeuvre.setTitreOeuvrepret(request.getParameter("txttitre"));
                        oeuvre.setProprietaire(
                                serviceProprietaire.consulterProprietaire(
                                        Integer.parseInt(request.getParameter("propId"))));

                        serviceOeuvre.insertOeuvrepret(oeuvre);
                    }

                    if(VENTE.equals(request.getParameter("type"))) {
                        Oeuvrevente oeuvre = new Oeuvrevente();

                        oeuvre.setTitreOeuvrevente(request.getParameter("txttitre"));
                        oeuvre.setEtatOeuvrevente(request.getParameter("txtetat"));
                        oeuvre.setPrixOeuvrevente(Float.parseFloat(request.getParameter("txtprix")));
                        oeuvre.setProprietaire(
                                serviceProprietaire.consulterProprietaire(
                                        Integer.parseInt(request.getParameter("propId"))));

                        serviceOeuvre.insertOeuvrevente(oeuvre);
                    }

                    destinationPage = setOeuvreListDestination(request);

                } catch (Exception e) {
                    request.setAttribute(
                            Constantes.ERROR_KEY,
                            Constantes.ERROR_INSERT.replace("%s", OEUVRE + request.getParameter("type")));
                }
                break;

            case DETAIL_OEUVRE_PRET:
                if(request.getParameter("id").isEmpty() || request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }

                try {
                    Oeuvrepret o = serviceOeuvre.consulterOeuvrePret(Integer.parseInt(request.getParameter("id")));

                    request.setAttribute("type", PRET);
                    request.setAttribute("oeuvre", o);
                    request.setAttribute("proprietaire", o.getProprietaire());
                    request.setAttribute("actionSubmit", "ControleurOeuvre?action=updateOeuvre");
                    destinationPage = "/content/oeuvre/formOeuvre.jsp";

                } catch(MonException e) {
                    request.setAttribute(
                            Constantes.ERROR_KEY,
                            Constantes.ERROR_DETAIL.replace("%s", OEUVRE + PRET));
                }
                break;

            case DETAIL_OEUVRE_VENTE:
                if(request.getParameter("id").isEmpty() || request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }

                try {
                    Oeuvrevente o = serviceOeuvre.consulterOeuvreVente(Integer.parseInt(request.getParameter("id")));

                    request.setAttribute("enumValues", Constantes.EtatsOeuvre.values());
                    request.setAttribute("type", VENTE);
                    request.setAttribute("oeuvre", o);
                    request.setAttribute("proprietaire", o.getProprietaire());
                    request.setAttribute("actionSubmit", "ControleurOeuvre?action=updateOeuvre");
                    destinationPage = "/content/oeuvre/formOeuvre.jsp";

                } catch(MonException e) {
                    request.setAttribute(
                            Constantes.ERROR_KEY,
                            Constantes.ERROR_DETAIL.replace("%s", OEUVRE + VENTE));
                }
                break;

            case UPDATE_OEUVRES:

                if(request.getParameter("type") == null
                        || request.getParameter("propId") == null
                        || request.getParameter("oeuvreId") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
                    break;
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
                        oeuvreToUpdate.setEtatOeuvrevente(request.getParameter("txtetat"));

                        serviceOeuvre.modifierOeuvrevente(oeuvreToUpdate);
                    }
                    destinationPage = setOeuvreListDestination(request);

                } catch(MonException e) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_UPDATING);
                }
                break;

            case DELETE_OEUVRES_PRET:
                if(request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }
                try {
                    serviceOeuvre.supprimerOeuvrepret(
                            serviceOeuvre.consulterOeuvrePret(Integer.parseInt(request.getParameter("id"))));
                    destinationPage = setOeuvreListDestination(request);
                } catch(Exception e){
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
                }
                break;

            case DELETE_OEUVRES_VENTE:
                if(request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }
                try {
                    serviceOeuvre.supprimerOeuvrevente(
                            serviceOeuvre.consulterOeuvreVente(Integer.parseInt(request.getParameter("id"))));
                    destinationPage = setOeuvreListDestination(request);
                } catch(Exception e){
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
                }
                break;

            default:
                String messageErreur = "[" + actionName + "] n'est pas une action valide.";
                request.setAttribute(Constantes.ERROR_KEY, messageErreur);
        }

        // Load la vue
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destinationPage);
        dispatcher.forward(request, response);
    }

    /***
     * Redirige vers la liste des Oeuvres
     * @param request
     * @return destinationPage
     */
    private String setOeuvreListDestination(HttpServletRequest request) {
        try {
            ServiceOeuvre unService = new ServiceOeuvre();
            request.setAttribute("mesOeuvresPret", unService.ConsulterListeOeuvrepret());
            request.setAttribute("mesOeuvresVente", unService.ConsulterListeOeuvrevente());
            return "/content/oeuvre/listerOeuvre.jsp";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING.replace("%s", OEUVRE));
        }
        return Constantes.ERROR_PAGE;
    }

}
