package dao;

import meserreurs.MonException;
import metier.Oeuvrepret;
import metier.Oeuvrevente;
import metier.Proprietaire;
import persistance.DialogueBd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lafay on 08/02/2017.
 */
public class ServiceOeuvre {
    public List<Oeuvrepret> ConsulterListeOeuvrepret(){
        List<Object> rs;
        List<Oeuvrepret> mesOeuvrepret = new ArrayList<Oeuvrepret>();
        String rq = "Select * from OeuvrePret;";

        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = DialogueBd.lecture(rq);
            while (index < rs.size()) {
                // On cr�e un stage
                Oeuvrepret uneO = new Oeuvrepret();
                // il faut redecouper la liste pour retrouver les lignes
                uneO.setIdOeuvrepret(Integer.parseInt(rs.get(index + 0).toString()));
                uneO.setTitreOeuvrepret(rs.get(index + 1).toString());
                uneO.setProprietaire(new ServiceProprietaire().consulterProprietaire(Integer.parseInt(rs.get(index + 2).toString())));
                // On incr�mente tous les 3 champs
                index = index + 3;
                mesOeuvrepret.add(uneO);
            }

            return mesOeuvrepret;
        } catch (Exception exc) {
            try {
                throw new MonException(exc.getMessage(), "systeme");
            } catch (MonException e) {
                e.printStackTrace();
            }
        }

        return mesOeuvrepret;
    }

    public List<Oeuvrevente> ConsulterListeOeuvrevente(){
        List<Object> rs;
        List<Oeuvrevente> mesOeuvrevente = new ArrayList<Oeuvrevente>();
        String rq = "Select * from OeuvreVente;";

        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = DialogueBd.lecture(rq);
            while (index < rs.size()) {
                // On cr�e un stage
                Oeuvrevente uneO = new Oeuvrevente();
                // il faut redecouper la liste pour retrouver les lignes
                uneO.setIdOeuvrevente(Integer.parseInt(rs.get(index + 0).toString()));
                uneO.setTitreOeuvrevente(rs.get(index + 1).toString());
                uneO.setEtatOeuvrevente(rs.get(index + 2).toString());
                uneO.setPrixOeuvrevente(Integer.parseInt(rs.get(index + 3).toString()));
                uneO.setProprietaire(new ServiceProprietaire().consulterProprietaire(Integer.parseInt(rs.get(index + 4).toString())));
                // On incr�mente tous les 3 champs
                index = index + 5;
                mesOeuvrevente.add(uneO);
            }

            return mesOeuvrevente;
        } catch (Exception exc) {
            try {
                throw new MonException(exc.getMessage(), "systeme");
            } catch (MonException e) {
                e.printStackTrace();
            }
        }

        return mesOeuvrevente;
    }

    public Oeuvrevente consulterOeuvreVente(int id){
        Oeuvrevente uneO = new Oeuvrevente();
        String rq = "Select * from oeuvreVente where idoeuvrevente ="+id;
        List<Object> rs;

        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = DialogueBd.lecture(rq);
            while (index < rs.size()) {
                // il faut redecouper la liste pour retrouver les lignes
                uneO.setIdOeuvrevente(Integer.parseInt(rs.get(index + 0).toString()));
                uneO.setTitreOeuvrevente(rs.get(index + 1).toString());
                uneO.setEtatOeuvrevente(rs.get(index + 2).toString());
                uneO.setPrixOeuvrevente(Integer.parseInt(rs.get(index + 3).toString()));
                uneO.setProprietaire(new ServiceProprietaire().consulterProprietaire(Integer.parseInt(rs.get(index + 4).toString())));
                // On incr�mente tous les 3 champs
                index = index + 5;
            }

            return uneO;
        } catch (Exception exc) {
            try {
                throw new MonException(exc.getMessage(), "systeme");
            } catch (MonException e) {
                e.printStackTrace();
            }
        }

        return uneO;
    }
}
