package controle;

import dao.ServiceAdherent;
import metier.Adherent;
import utilitaires.Constantes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/ControleurAdherent")
public class ControleurAdherent extends HttpServlet{

    public static final String ADHERENT = "adhérent";
    private static final String LISTER_ADHERENT = "listerAdherent";
    private static final String AJOUTER_ADHERENT = "ajouterAdherent";
    private static final String INSERER_ADHERENT = "insererAdherent";
    private static final String DETAIL_ADHERENT = "detailAdherent";
    private static final String UPDATE_ADHERENT = "updateAdherent";
    private static final String SUPPRIMER_ADHERENT = "supprimerAdherent";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurAdherent() {
        super();
        // TODO Auto-generated constructor stub
    }

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

        ServiceAdherent service = new ServiceAdherent();
        String actionName = request.getParameter(Constantes.ACTION_TYPE);
        String destinationPage = Constantes.ERROR_PAGE;

        switch(actionName) {
            case LISTER_ADHERENT :
                destinationPage = setAdherentsListDestination(request);
                break;

            case AJOUTER_ADHERENT:
                request.setAttribute("actionSubmit", "ControleurAdherent?action=" + INSERER_ADHERENT);
                destinationPage = "/content/adherent/formAdherent.jsp";
                break;

            case INSERER_ADHERENT:
                try {
                    Adherent unAdherent = new Adherent();
                    unAdherent.setNomAdherent(request.getParameter("txtnom"));
                    unAdherent.setPrenomAdherent(request.getParameter("txtprenom"));
                    unAdherent.setVilleAdherent(request.getParameter("txtville"));

                    service.insertAdherent(unAdherent);
                    destinationPage = setAdherentsListDestination(request);

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_INSERT.replace("%s", ADHERENT));
                }
                break;

            case DETAIL_ADHERENT:
                try {
                    if (request.getParameter("id").isEmpty() || request.getParameter("id") == null) {
                        request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                        break;
                    }

                    Adherent a = service.consulterAdherent(Integer.parseInt(request.getParameter("id")));

                    if (a == null) {
                        request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL.replace("%s", ADHERENT));
                        break;
                    }

                    request.setAttribute("adherent", a);
                    request.setAttribute("actionSubmit", "ControleurAdherent?action=" + UPDATE_ADHERENT);
                    destinationPage = "/content/adherent/formAdherent.jsp";

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DETAIL.replace("%s", ADHERENT));
                }
                break;

            case UPDATE_ADHERENT:

                if(request.getParameter("txtId") == null
                        || request.getParameter("txtnom").isEmpty()
                        || request.getParameter("txtprenom").isEmpty()
                        || request.getParameter("txtville").isEmpty()) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
                    break;
                }

                try {

                    Adherent adherentToUpdate = service
                            .consulterAdherent(Integer.parseInt(request.getParameter("txtId")));
                    adherentToUpdate.setNomAdherent(request.getParameter("txtnom"));
                    adherentToUpdate.setPrenomAdherent(request.getParameter("txtprenom"));
                    adherentToUpdate.setVilleAdherent(request.getParameter("txtville"));
                    service.modifierAdherent(adherentToUpdate);
                    destinationPage = setAdherentsListDestination(request);

                } catch(Exception e) {
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_UPDATING);
                }
                break;

            case SUPPRIMER_ADHERENT:
                if(request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }
                try {
                    service.supprimerAdherent(service.consulterAdherent(Integer.parseInt(request.getParameter("id"))));
                    destinationPage = setAdherentsListDestination(request);
                } catch(Exception e){
                    e.printStackTrace();
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_DELETING);
                }
                break;

            default:
                String messageErreur = "[" + actionName + "] n'est pas une action valide.";
                request.setAttribute(Constantes.ERROR_KEY, messageErreur);
        }

        // Redirection vers la page jsp appropriee
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destinationPage);
        dispatcher.forward(request, response);
    }

    /***
     * Redirige vers la liste des adhérents
     * @param request
     * @return destinationPage
     */
    private String setAdherentsListDestination(HttpServletRequest request) {
        try {
            ServiceAdherent unService = new ServiceAdherent();
            request.setAttribute("mesAdherents", unService.consulterListeAdherents());
            return "/content/adherent/listerAdherent.jsp";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return Constantes.ERROR_PAGE;
    }
}
