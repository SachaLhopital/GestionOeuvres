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

    //region oeuvre pret

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

    public Oeuvrepret insertOeuvrepret(Oeuvrepret oeuvre){
        String rq = "insert into oeuvrepret (id_proprietaire, titre_oeuvrepret) values ("+
                oeuvre.getProprietaire().getIdProprietaire()+","+
                "'"+oeuvre.getTitreOeuvrepret()+"'";

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.insertionBD(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        //recuperation de l'ID
        rq = "select LAST_INSERT_ID();";

        List<Object> rs = new ArrayList<>();
        try
        {
            rs = DialogueBd.lecture(rq);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        if(rs.size()<=0){
            return null;
        }

        oeuvre.setIdOeuvrepret(Integer.parseInt(rs.get(0).toString()));

        return oeuvre;
    }

    public void supprimerOeuvrepret (Oeuvrepret oeuvre){
        String rq = "delete from oeuvrepret where id_oeuvrepret ="+oeuvre.getIdOeuvrepret();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
        }
    }

    public void supprimerOeuvrepret(Proprietaire prop){
        String rq = "delete from oeuvrepret where id_proprietaire ="+prop.getIdProprietaire();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
        }
    }

    public Oeuvrepret modifierOeuvrepret(Oeuvrepret oeuvre){
        String rq = "update oeuvrepret set "+
                "id_proprietaire ="+oeuvre.getProprietaire().getIdProprietaire()+
                ", titre_oeuvrepret='"+oeuvre.getTitreOeuvrepret()+"'"+
                " where id_oeuvrepret="+oeuvre.getIdOeuvrepret();
        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            return null;
        }
        return oeuvre;
    }
    //endregion



    //region oeuvrevente
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

    public Oeuvrevente insererOeuvrevente (Oeuvrevente oeuvre){
        String rq = "inset into oeuvrevente (etat_oeuvrevente, id_proprietaire, prix_oeuvrevente, titre_oeuvrevente) Values ("+
                "'"+oeuvre.getEtatOeuvrevente()+"',"+
                oeuvre.getProprietaire().getIdProprietaire()+","+
                oeuvre.getPrixOeuvrevente()+","+
                "'"+oeuvre.getTitreOeuvrevente()+"'";

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.insertionBD(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        //recuperation de l'ID
        rq = "select LAST_INSERT_ID();";

        List<Object> rs = new ArrayList<>();
        try
        {
            rs = DialogueBd.lecture(rq);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        if(rs.size()<=0){
            return null;
        }

        oeuvre.setIdOeuvrevente(Integer.parseInt(rs.get(0).toString()));

        return oeuvre;
    }

    public Oeuvrevente modifierOeuvrevente(Oeuvrevente oeuvre){
        String rq = "Update oeuvrevente set"+
                "etat_oeuvrevente='"+oeuvre.getEtatOeuvrevente()+"',"+
                "id_proprietaire="+oeuvre.getProprietaire().getIdProprietaire()+","+
                "prix_oeuvrevente="+oeuvre.getPrixOeuvrevente()+","+
                "titre_oeuvrevente='"+oeuvre.getTitreOeuvrevente()+"'"+
                " where id_oeuvrevente="+oeuvre.getIdOeuvrevente();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            return null;
        }

        return oeuvre;

    }

    public void supprimerOeuvrevente (Oeuvrevente oeuvre){
        String rq = "Delete from oeuvrevente where id_oeuvrevente ="+oeuvre.getIdOeuvrevente();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
        }
    }

    public void supprimerOeuvrevente(Proprietaire prop){
        String rq = "delete from oeuvrevente where id_proprietaire="+prop.getIdProprietaire();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
