package controle;

import dao.ServiceOeuvre;
import dao.ServiceProprietaire;
import metier.Oeuvrepret;
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
    private static final String DETAIL_OEUVRES = "detailOeuvre";
    private static final String UPDATE_OEUVRES = "updateOeuvre";
    private static final String DELETE_OEUVRES = "deleteOeuvre";

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
                        destinationPage = setOeuvreListDestination(request);
                    }

                    if(VENTE.equals(request.getParameter("type"))) {
                       //todo
                    }

                } catch (Exception e) {
                    request.setAttribute(
                            Constantes.ERROR_KEY,
                            Constantes.ERROR_INSERT.replace("%s", OEUVRE + request.getParameter("type")));
                }
                break;

            case DETAIL_OEUVRES:
                if(request.getParameter("id").isEmpty() || request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }

                /*Oeuvre p = service.consulterProprietaire(Integer.parseInt(request.getParameter("id")));

                if(p == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL);
                    break;
                }

                request.setAttribute("proprietaire", p);
                request.setAttribute("actionSubmit", "ControleurProprietaire?action=updateProprietaire");
                destinationPage = "/content/proprietaire/formProprietaire.jsp";*/
                break;

            case UPDATE_OEUVRES:

                if(request.getParameter("txtId") == null || request.getParameter("txtnom").isEmpty()) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }

                /*Proprietaire proprietaireToUpdate = service
                        .consulterProprietaire(Integer.parseInt(request.getParameter("txtId")));
                proprietaireToUpdate.setNomProprietaire(request.getParameter("txtnom"));
                proprietaireToUpdate.setPrenomProprietaire(request.getParameter("txtprenom"));
                service.modifierProprietaire(proprietaireToUpdate);
                destinationPage = setProprietaireListDestination(request);*/
                break;

            case DELETE_OEUVRES:
                if(request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }
                /*try {
                    service.supprimerProprietaire(
                            service.consulterProprietaire(Integer.parseInt(request.getParameter("id"))));
                    destinationPage = setProprietaireListDestination(request);
                } catch(Exception e){
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
                }*/
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
