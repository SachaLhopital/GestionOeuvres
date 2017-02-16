package controle;

import dao.ServiceProprietaire;
import metier.Proprietaire;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lafay on 16/02/2017.
 */
@WebServlet("/ControleurProprietaire")
public class ControleurProprietaire extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String ACTION_TYPE = "action";
    private static final String LISTER_PROPRIETAIRE = "listerProprietaire";
    private static final String AJOUTER_PROPRIETAIRE = "ajouterProprietaire";
    private static final String INSERER_PROPRIETAIRE = "insererProprietaire";
    private static final String DETAIL_PROPRIETAIRE = "detailProprietaire";
    private static final String UPDATE_PROPRIETAIRE = "updateProprietaire";

    private static final String ERROR_KEY = "messageErreur";
    private static final String ERROR_PAGE = "/erreur.jsp";
    private static final String ERROR_LISTING = "Impossible de récupérer la liste des propriétaires";
    private static final String ERROR_INSERT = "Impossible d'insérer un propriétaire";
    private static final String ERROR_ID_MISSING = "Id manquant";
    private static final String ERROR_DETAIL_PROPRIETAIRE = "Impossible d'obtenir les détails du propriétaire";

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

        String actionName = request.getParameter(ACTION_TYPE);
        String destinationPage = ERROR_PAGE;

        switch (actionName) {
            case LISTER_PROPRIETAIRE:
                destinationPage = setProprietaireListDestination(request);
                break;

            case AJOUTER_PROPRIETAIRE:
                request.setAttribute("actionSubmit", "ControleurProprietaire?action=insererProprietaire");
                destinationPage = "/content/proprietaire/formProprietaire.jsp";
                break;

            case INSERER_PROPRIETAIRE:
                try {
                    Proprietaire unProprietaire = new Proprietaire();
                    unProprietaire.setNomProprietaire(request.getParameter("txtnom"));
                    unProprietaire.setPrenomProprietaire(request.getParameter("txtprenom"));

                    ServiceProprietaire unService = new ServiceProprietaire();
                    unService.insererProprietaire(unProprietaire);
                    destinationPage = setProprietaireListDestination(request);

                } catch (Exception e) {
                    request.setAttribute(ERROR_KEY, ERROR_INSERT);
                }
                break;

            case DETAIL_PROPRIETAIRE:
                if(request.getParameter("id").isEmpty() || request.getParameter("id") == null) {
                    request.setAttribute(ERROR_KEY, ERROR_ID_MISSING);
                    break;
                }

                Proprietaire p = new ServiceProprietaire()
                        .consulterProprietaire(Integer.parseInt(request.getParameter("id")));

                if(p == null) {
                    request.setAttribute(ERROR_KEY, ERROR_DETAIL_PROPRIETAIRE);
                    break;
                }

                request.setAttribute("proprietaire", p);
                request.setAttribute("actionSubmit", "ControleurProprietaire?action=updateProprietaire");
                destinationPage = "/content/proprietaire/formProprietaire.jsp";
                break;

            case UPDATE_PROPRIETAIRE:

                if(request.getParameter("txtId") == null || request.getParameter("txtnom").isEmpty()) {
                    request.setAttribute(ERROR_KEY, ERROR_ID_MISSING);
                    break;
                }

                ServiceProprietaire service = new ServiceProprietaire();

                Proprietaire proprietaireToUpdate = service
                        .consulterProprietaire(Integer.parseInt(request.getParameter("txtId")));
                proprietaireToUpdate.setNomProprietaire(request.getParameter("txtnom"));
                proprietaireToUpdate.setPrenomProprietaire(request.getParameter("txtprenom"));
                service.modifierProprietaire(proprietaireToUpdate);
                destinationPage = setProprietaireListDestination(request);
                break;

            default:
                String messageErreur = "[" + actionName + "] n'est pas une action valide.";
                request.setAttribute(ERROR_KEY, messageErreur);
        }

        // Load la vue
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destinationPage);
        dispatcher.forward(request, response);
    }

    /***
     * Redirige vers la liste des propriétaires
     * @param request
     * @return destinationPage
     */
    private String setProprietaireListDestination(HttpServletRequest request) {
        try {
            ServiceProprietaire unService = new ServiceProprietaire();
            request.setAttribute("mesProprietaires", unService.consulterProprietaires());
            return "/content/proprietaire/listerProprietaire.jsp";
        } catch (Exception e) {
            request.setAttribute(ERROR_KEY, ERROR_LISTING);
        }
        return ERROR_PAGE;
    }

}
