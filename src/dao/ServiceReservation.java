package dao;

import meserreurs.MonException;
import metier.Proprietaire;
import metier.Reservation;
import persistance.DialogueBd;
import utilitaires.FonctionsUtiles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lafay on 08/02/2017.
 */
public class ServiceReservation {
    public List<Reservation> consulterProprietaires(){
        return consulterProprietaires("Select * fom Proprietaire;");
    }

    public Reservation consulterProprietaire(int id){
        /*List<Reservation> reservations = consulterReservations("Select * fom Reservation where idproprietaire = "+id+";");
        if(proprietaires.size()<1){
            return null;
        }
        return proprietaires.get(0);*/
    }

    private List<Reservation> consulterReservations(String mysql){
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
                uneR.setDate(FonctionsUtiles.conversionChaineenDate(rs.get(index).toString(),"yyyy-MM-dd"));
                uneR.setAdherent(new ServiceAdherent().consulterAdherent(Integer.parseInt(rs.get(index+1).toString())));
                uneR.setOeuvrevente(new ServiceOeuvre().consulterOeuvreVente(Integer.parseInt(rs.get(index+2).toString())));
                index = index + 2;
                mesReservations.add(uneR);
            }

            return mesReservations;
        } catch (Exception exc) {
            try {
                throw new MonException(exc.getMessage(), "systeme");
            } catch (MonException e) {
                e.printStackTrace();
            }
        }

        return mesReservations;
    }
}
