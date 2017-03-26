package com.epul.oeuvres.dao;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Adherent;
import com.epul.oeuvres.metier.Oeuvrevente;
import com.epul.oeuvres.metier.Proprietaire;
import com.epul.oeuvres.metier.Reservation;
import com.epul.oeuvres.persistance.DialogueBd;
import com.epul.oeuvres.utilitaires.FonctionsUtiles;
import com.epul.oeuvres.utilitaires.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachouw on 11/03/2017.
 */
public class ServiceReservation {

    public List<Reservation> consulterReservations() throws MonException {
        return consulterReservations("Select * from Reservation;");
    }

    public Reservation getReservationByDateAndOeuvre(String date, int idOeuvre) throws MonException {
        try {
            List<Reservation> reservations = consulterReservations(
                    "Select * " +
                            "from Reservation " +
                            "where id_oeuvrevente = " + idOeuvre +
                            " and date_reservation = '" + date + "';");
            if (reservations.size() < 1) {
                throw new MonException(
                        "Aucune réservation trouvée pour cette date et cette oeuvre",
                        "service");
            }
            return reservations.get(0);
        } catch (MonException e) {
            throw new MonException(e.getMessage(), "erreur interne");
        }
    }

    /***
     * Réupère la sortie d'une requête sous forme d'une liste de réservations
     * @param mysql la requête à executer
     * @return Liste de réservations
     * @throws MonException
     */
    private List<Reservation> consulterReservations(String mysql) throws MonException {
        List<Reservation> mesReservations = new ArrayList<Reservation>();
        List<Object> rs;

        int index = 0;
        try {
            rs = DialogueBd.getInstance().lecture(mysql);
            while (index < rs.size()) {
                // On cr�e un stage
                Reservation uneR = new Reservation();
                // il faut redecouper la liste pour retrouver les lignes
                uneR.setOeuvrevente(new ServiceOeuvre().consulterOeuvreVente(Integer.parseInt(rs.get(index).toString())));
                uneR.setAdherent(new ServiceAdherent().consulterAdherent(Integer.parseInt(rs.get(index + 1).toString())));
                uneR.setDate(FonctionsUtiles.conversionChaineenDate(rs.get(index + 2).toString(), "yyyy-MM-dd"));
                index = index + 4;//3 elements + statut non pris en compte dans l'affichage
                mesReservations.add(uneR);
            }
            return mesReservations;
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "Systeme");
        }
    }

    /***
     * Supprime une réservation spécifique
     * @param resa
     * @throws MonException
     */
    public void supprimerReservation(Reservation resa) throws MonException {
        try {
            String rq = "delete from reservation where id_oeuvrevente=" + resa.getOeuvrevente().getIdOeuvrevente() +
                    " and date_reservation='" + FonctionsUtiles.DateToString(resa.getDate(), "yyyy-MM-dd") + "'";

            DialogueBd unDialogueBd = DialogueBd.getInstance();
            unDialogueBd.execute(rq);

            //L'oeuvre redeviens disponible
            resa.getOeuvrevente().setEtatOeuvrevente(Constantes.EtatsOeuvre.L.toString());
            new ServiceOeuvre().modifierOeuvrevente(resa.getOeuvrevente());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "erreur suppression reservation");
        }
    }

    /***
     * Suppression les réservations associées à un adhérent
     * (+ l'oeuvre redeviens disponible)
     * @param adherent
     */
    public void supprimerReservation(Adherent adherent) throws MonException {
        try {
            List<Reservation> adherentReservations =
                    consulterReservations("SELECT * " +
                        "FROM Reservation " +
                        "WHERE id_adherent = " + adherent.getIdAdherent());

            for(Reservation resa : adherentReservations) {
                supprimerReservation(resa);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MonException (ex.getMessage(), "Erreur suppression des réservations d'un adhérents");
        }
    }

    /***
     * Suppression des réservations associées à une oeuvre
     * @param oeuvre
     */
    public void supprimerReservation(Oeuvrevente oeuvre) throws MonException {
        String rq = "delete from reservation where id_oeuvrevente=" + oeuvre.getIdOeuvrevente();

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try {
            unDialogueBd.execute(rq);
        } catch (Exception ex) {
            throw new MonException(ex.getMessage());
        }
    }

    /**
     * Suppression des reservations lié a une oeuvre lié a un proprietaire
     * @param prop
     */
    public void supprimerReservation(Proprietaire prop) throws MonException {
        String rq = "delete from reservation where id_oeuvrevente in " +
                "(select id_oeuvrevente from oeuvrevente where id_proprietaire = " + prop.getIdProprietaire() + ")";

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try {
            unDialogueBd.execute(rq);
        } catch (Exception ex) {
            throw new MonException(ex.getMessage());
        }
    }

    public Reservation insererReservation(Reservation resa) throws MonException {
        try {
            String rq = "Insert into Reservation (date_reservation, id_adherent, id_oeuvrevente, statut) values (" +
                    "'" + FonctionsUtiles.DateToString(resa.getDate(), "yyyy-MM-dd") + "'," +
                    resa.getAdherent().getIdAdherent() + "," +
                    resa.getOeuvrevente().getIdOeuvrevente() + "," +
                    "'confirme');";

            DialogueBd.getInstance().insertionBD(rq);

            //On change l'état de l'oeuvre de "libre" à "reservé"
            resa.getOeuvrevente().setEtatOeuvrevente(Constantes.EtatsOeuvre.R.toString());
            new ServiceOeuvre().modifierOeuvrevente(resa.getOeuvrevente());

        } catch (Exception ex) {
            throw new MonException(ex.getMessage());
        }
        return resa;
    }
}
