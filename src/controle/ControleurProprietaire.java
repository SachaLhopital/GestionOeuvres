package controle;

import dao.ServiceAdherent;
import dao.ServiceProprietaire;
import meserreurs.MonException;
import metier.Adherent;
import metier.Proprietaire;

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
    private static final String LISTER_PROPRIETAIRE= "listerProprietaire";
    private static final String AJOUTER_PROPRIETAIRE = "ajouterProprietaire";
    private static final String INSERER_PROPRIETAIRE = "insererProprietaire";

    private static final String ERROR_KEY = "messageErreur";
    private static final String ERROR_PAGE = "/erreur.jsp";

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
                try {

                    ServiceProprietaire unService = new ServiceProprietaire();
                    request.setAttribute("mesProprietaires", unService.consulterProprietaires());

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                destinationPage = "/content/proprietaire/listerProprietaire.jsp";
                break;

            case AJOUTER_PROPRIETAIRE:
                destinationPage = "/content/proprietaire/ajouterProprietaire.jsp";
                break;

            case INSERER_PROPRIETAIRE:
                try {
                    Proprietaire unProprietaire = new Proprietaire();
                    unProprietaire.setNomProprietaire(request.getParameter("txtnom"));
                    unProprietaire.setPrenomProprietaire(request.getParameter("txtprenom"));

                    ServiceProprietaire unService = new ServiceProprietaire();
                    unService.insererProprietaire(unProprietaire);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                destinationPage = "/index.jsp";
                break;

            default:
                String messageErreur = "[" + actionName + "] n'est pas une action valide.";
                request.setAttribute(ERROR_KEY, messageErreur);
        }
    }

}
