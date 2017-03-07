package dao;

import meserreurs.MonException;
import metier.Adherent;
import metier.Reservation;
import persistance.DialogueBd;
import utilitaires.Constantes;
import utilitaires.FonctionsUtiles;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lafay on 08/02/2017.
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
        }catch(MonException e) {
            throw new MonException(e.getMessage(), "erreur interne");
        }/*catch(ParseException e) {
            throw new MonException(e.getMessage(), "parsing d'une date");
        }*/
    }

    private List<Reservation> consulterReservations(String mysql) throws MonException {
        List<Reservation> mesReservations = new ArrayList<Reservation>();
        List<Object> rs;

        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = DialogueBd.lecture(mysql);
            while (index < rs.size()) {
                // On cr�e un stage
                Reservation uneR = new Reservation();
                // il faut redecouper la liste pour retrouver les lignes
                uneR.setOeuvrevente(new ServiceOeuvre().consulterOeuvreVente(Integer.parseInt(rs.get(index).toString())));
                uneR.setAdherent(new ServiceAdherent().consulterAdherent(Integer.parseInt(rs.get(index+1).toString())));
                uneR.setDate(FonctionsUtiles.conversionChaineenDate(rs.get(index+2).toString(),"yyyy-MM-dd"));
                index = index + 4;//3 elements + statut non pris en compte dans l'affichage
                mesReservations.add(uneR);
            }
            return mesReservations;
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "systeme");
        }
    }

    public void supprimerReservation(Reservation resa) throws MonException {
        try{
            String rq = "delete from reservation where id_oeuvrevente="+resa.getOeuvrevente().getIdOeuvrevente()+
                    " and date_reservation='"+FonctionsUtiles.DateToString(resa.getDate(),"yyyy-MM-dd") + "'";

            DialogueBd unDialogueBd = DialogueBd.getInstance();
            unDialogueBd.execute(rq);

            //L'oeuvre redeviens disponible
            resa.getOeuvrevente().setEtatOeuvrevente(Constantes.EtatsOeuvre.L.toString());
            new ServiceOeuvre().modifierOeuvrevente(resa.getOeuvrevente());

        }catch(Exception ex){
            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "erreur suppression reservation");
        }
    }

    public void supprimerReservation(Adherent adherent){
        String rq = "delete from reservation where idadherent="+adherent.getIdAdherent();

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.execute(rq);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Reservation insererReservation (Reservation resa) throws MonException {
        try{
            String rq = "Insert into Reservation (date_reservation, id_adherent, id_oeuvrevente, statut) values ("+
                    "'"+ FonctionsUtiles.DateToString(resa.getDate(),"yyyy-MM-dd")+"',"+
                    resa.getAdherent().getIdAdherent()+","+
                    resa.getOeuvrevente().getIdOeuvrevente()+","+
                    "'confirme');";

            DialogueBd.getInstance().insertionBD(rq);

            //On change l'état de l'oeuvre de "libre" à "reservé"
            resa.getOeuvrevente().setEtatOeuvrevente(Constantes.EtatsOeuvre.R.toString());
            new ServiceOeuvre().modifierOeuvrevente(resa.getOeuvrevente());

        }catch(Exception ex) {
            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "insertion reservation");
        }
        return resa;
    }


}
