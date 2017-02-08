package dao;

import meserreurs.MonException;
import metier.Proprietaire;
import persistance.DialogueBd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lafay on 08/02/2017.
 */
public class ServiceProprietaire {
    public List<Proprietaire> consulterProprietaires(){
        return consulterProprietaires("Select * fom Proprietaire;");
    }

    public Proprietaire consulterProprietaire(int id){
        List<Proprietaire> proprietaires = consulterProprietaires("Select * fom Proprietaire where idproprietaire = "+id+";");
        if(proprietaires.size()<1){
            return null;
        }
        return proprietaires.get(0);
    }

    private List<Proprietaire> consulterProprietaires(String mysql){
        List<Proprietaire> mesProprietaires = new ArrayList<Proprietaire>();

        List<Object> rs;

        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = DialogueBd.lecture(mysql);
            while (index < rs.size()) {
                // On cr�e un stage
                Proprietaire unP = new Proprietaire();
                // il faut redecouper la liste pour retrouver les lignes
                unP.setIdProprietaire(Integer.parseInt(rs.get(index + 0).toString()));
                unP.setNomProprietaire(rs.get(index + 1).toString());
                unP.setPrenomProprietaire(rs.get(index + 2).toString());
                // On incr�mente tous les 3 champs
                index = index + 3;
                mesProprietaires.add(unP);
            }

            return mesProprietaires;
        } catch (Exception exc) {
            try {
                throw new MonException(exc.getMessage(), "systeme");
            } catch (MonException e) {
                e.printStackTrace();
            }
        }

        return mesProprietaires;
    }
}
