package controle;

import dao.ServiceAdherent;
import dao.ServiceOeuvre;
import dao.ServiceReservation;
import meserreurs.MonException;
import metier.Reservation;
import utilitaires.Constantes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/ControleurReservation")
public class ControleurReservation extends HttpServlet {

    private static final String RESERVATION = "Reservation";
    private static final String LISTER_RESERVATION = "listerReservation";
    private static final String AJOUTER_RESERVATION = "ajouterReservation";
    private static final String INSERER_RESERVATION = "insererReservation";
    private static final String DELETE_RESERVATION = "deleteReservation";

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

        ServiceReservation serviceReservation = new ServiceReservation();
        ServiceAdherent serviceAdherent = new ServiceAdherent();
        ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
        String actionName = request.getParameter(Constantes.ACTION_TYPE);
        String destinationPage = Constantes.ERROR_PAGE;

        switch (actionName) {
            case LISTER_RESERVATION:
                destinationPage = setReservationListDestination(request);
                break;

            case AJOUTER_RESERVATION:
                if(request.getParameter("id") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
                }

                try {

                    request.setAttribute("adherent",
                            serviceAdherent.consulterAdherent(Integer.parseInt(request.getParameter("id"))));
                    request.setAttribute("oeuvresvente", serviceOeuvre.GetListeOeuvreventeLibres());
                    request.setAttribute("actionSubmit", "ControleurReservation?action=insererReservation");
                    destinationPage = "/content/reservation/formReservation.jsp";

                }catch(MonException e) {
                    request.setAttribute(
                            Constantes.ERROR_KEY,
                            Constantes.ERROR_LISTING.replace("%s", "Formulaire Reservation"));
                }
                break;

            case INSERER_RESERVATION:
                if(request.getParameter("idAdherent") == null || request.getParameter("txtOeuvre") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_ID_MISSING);
                    break;
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
                    destinationPage = setReservationListDestination(request);

                } catch (Exception e) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_INSERT);
                }
                break;

            case DELETE_RESERVATION:
                if(request.getParameter("id") == null || request.getParameter("date") == null) {
                    request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_MISSING_ARGS);
                    break;
                }

                try {
                    serviceReservation.supprimerReservation(
                            serviceReservation.getReservationByDateAndOeuvre(
                                    request.getParameter("date"),
                                    Integer.parseInt(request.getParameter("id"))));
                    destinationPage = setReservationListDestination(request);
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
     * Redirige vers la liste des propriétaires
     * @param request
     * @return destinationPage
     */
    private String setReservationListDestination(HttpServletRequest request) {
        try {
            request.setAttribute("mesReservations", new ServiceReservation().consulterReservations());
            return "/content/reservation/listerReservation.jsp";
        } catch (Exception e) {
            request.setAttribute(Constantes.ERROR_KEY, Constantes.ERROR_LISTING);
        }
        return Constantes.ERROR_PAGE;
    }

}
