package com.epul.oeuvres.dao;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Oeuvrepret;
import com.epul.oeuvres.metier.Oeuvrevente;
import com.epul.oeuvres.metier.Proprietaire;
import com.epul.oeuvres.persistance.DialogueBd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachouw on 11/03/2017.
 */
public class ServiceOeuvre {
    //region oeuvre pret

    public List<Oeuvrepret> ConsulterListeOeuvrepret() throws MonException {
        List<Object> rs;
        List<Oeuvrepret> mesOeuvrepret = new ArrayList<Oeuvrepret>();
        String rq = "Select * from OeuvrePret;";

        int index = 0;
        try {
            rs = DialogueBd.getInstance().lecture(rq);
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
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "systeme");
        }
        return mesOeuvrepret;
    }

    public Oeuvrepret consulterOeuvrePret(int id) throws MonException {
        Oeuvrepret uneO = new Oeuvrepret();
        String rq = "Select * from oeuvrePret where id_oeuvrepret ="+id;
        List<Object> rs;

        int index = 0;
        try {
            rs = DialogueBd.getInstance().lecture(rq);
            while (index < rs.size()) {

                // il faut redecouper la liste pour retrouver les lignes
                uneO.setIdOeuvrepret(Integer.parseInt(rs.get(index + 0).toString()));
                uneO.setTitreOeuvrepret(rs.get(index + 1).toString());
                uneO.setProprietaire(
                        new ServiceProprietaire().consulterProprietaire(
                                Integer.parseInt(rs.get(index + 2).toString())));
                //incrémentation
                index = index + 3;
            }
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "model");
        }
        return uneO;
    }

    public Oeuvrepret insertOeuvrepret(Oeuvrepret oeuvre) throws MonException {
        String rq = "insert into oeuvrepret (id_proprietaire, titre_oeuvrepret) values ("+
                oeuvre.getProprietaire().getIdProprietaire()+","+
                "'"+oeuvre.getTitreOeuvrepret()+"')";

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.insertionBD(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "model");
        }

        //recuperation de l'ID
        rq = "select LAST_INSERT_ID();";

        List<Object> rs = new ArrayList<>();
        try
        {
            rs = unDialogueBd.lecture(rq);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "model");
        }

        if(rs.size()<=0){
            throw new MonException("Aucun element dans la table oeuvre", "model");
        }

        oeuvre.setIdOeuvrepret(Integer.parseInt(rs.get(0).toString()));

        return oeuvre;
    }

    public void supprimerOeuvrepret (Oeuvrepret oeuvre) throws MonException {
        String rq = "delete from oeuvrepret where id_oeuvrepret ="+oeuvre.getIdOeuvrepret();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            throw new MonException(e.getMessage(), "model");
        }
    }

    public void supprimerOeuvrepret(Proprietaire prop) throws MonException {
        String rq = "delete from oeuvrepret where id_proprietaire ="+prop.getIdProprietaire();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            throw new MonException(e.getMessage(), "model");
        }
    }

    public Oeuvrepret modifierOeuvrepret(Oeuvrepret oeuvre) throws MonException {
        String rq = "update oeuvrepret set "+
                "id_proprietaire ="+oeuvre.getProprietaire().getIdProprietaire()+
                ", titre_oeuvrepret='"+oeuvre.getTitreOeuvrepret()+"'"+
                " where id_oeuvrepret="+oeuvre.getIdOeuvrepret();
        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            throw new MonException(e.getMessage(), "model");
        }
        return oeuvre;
    }
    //endregion



    //region oeuvrevente
    public List<Oeuvrevente> ConsulterListeOeuvrevente() throws MonException {
        return buildOeuvresVentesFromRQ("Select * from OeuvreVente;");
    }

    public List<Oeuvrevente> GetListeOeuvreventeLibres() throws MonException {
        return buildOeuvresVentesFromRQ("Select * " +
                "from OeuvreVente " +
                "where etat_oeuvrevente <> 'R'" +
                "ORDER BY titre_oeuvrevente;");
    }

    public Oeuvrevente consulterOeuvreVente(int id) throws MonException {
        return buildOeuvresVentesFromRQ("Select * from oeuvreVente where id_oeuvrevente ="+id).get(0);
    }

    public Oeuvrevente insertOeuvrevente (Oeuvrevente oeuvre) throws MonException{
        String rq = "insert into oeuvrevente (etat_oeuvrevente, id_proprietaire, prix_oeuvrevente, titre_oeuvrevente) Values ("+
                "'"+oeuvre.getEtatOeuvrevente()+"',"+
                oeuvre.getProprietaire().getIdProprietaire()+","+
                oeuvre.getPrixOeuvrevente()+","+
                "'"+oeuvre.getTitreOeuvrevente()+"')";

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.insertionBD(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "model");
        }

        //recuperation de l'ID
        rq = "select LAST_INSERT_ID();";

        List<Object> rs = new ArrayList<>();
        try
        {
            rs = unDialogueBd.lecture(rq);
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

    public Oeuvrevente modifierOeuvrevente(Oeuvrevente oeuvre) throws MonException {
        String rq = "Update oeuvrevente set "+
                "etat_oeuvrevente='"+oeuvre.getEtatOeuvrevente()+"',"+
                "id_proprietaire="+oeuvre.getProprietaire().getIdProprietaire()+","+
                "prix_oeuvrevente="+oeuvre.getPrixOeuvrevente()+","+
                "titre_oeuvrevente='"+oeuvre.getTitreOeuvrevente()+"'"+
                " where id_oeuvrevente='"+oeuvre.getIdOeuvrevente() +"';";

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            throw new MonException(e.getMessage(), "model");
        }
        return oeuvre;
    }

    public void supprimerOeuvrevente (Oeuvrevente oeuvre) throws MonException {

        //suppression des reservations associés
        new ServiceReservation().supprimerReservation(oeuvre);

        String rq = "Delete from oeuvrevente where id_oeuvrevente ="+oeuvre.getIdOeuvrevente();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            throw new MonException(e.getMessage(), "model");
        }
    }

    public void supprimerOeuvrevente(Proprietaire prop) throws MonException {

        //suppression des reservations associés
        new ServiceReservation().supprimerReservation(prop);

        String rq = "delete from oeuvrevente where id_proprietaire="+prop.getIdProprietaire();

        try {
            DialogueBd.getInstance().execute(rq);
        } catch (MonException e) {
            e.printStackTrace();
            throw new MonException(e.getMessage(), "model");
        }
    }

    private List<Oeuvrevente> buildOeuvresVentesFromRQ(String rq) throws MonException {
        int index = 0;
        List<Object> rs;
        List<Oeuvrevente> mesOeuvrevente = new ArrayList<Oeuvrevente>();
        try {
            rs = DialogueBd.getInstance().lecture(rq);
            while (index < rs.size()) {
                // On cr�e un stage
                Oeuvrevente uneO = new Oeuvrevente();
                // il faut redecouper la liste pour retrouver les lignes
                uneO.setIdOeuvrevente(Integer.parseInt(rs.get(index + 0).toString()));
                uneO.setTitreOeuvrevente(rs.get(index + 1).toString());
                uneO.setEtatOeuvrevente(rs.get(index + 2).toString());
                uneO.setPrixOeuvrevente(Float.parseFloat(rs.get(index + 3).toString()));
                uneO.setProprietaire(new ServiceProprietaire().consulterProprietaire(Integer.parseInt(rs.get(index + 4).toString())));
                // On incr�mente tous les 3 champs
                index = index + 5;
                mesOeuvrevente.add(uneO);
            }
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "systeme");
        }
        return mesOeuvrevente;
    }
    //endregion
}
