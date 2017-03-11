package com.epul.oeuvres.dao;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Proprietaire;
import com.epul.oeuvres.persistance.DialogueBd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachouw on 11/03/2017.
 */
public class ServiceProprietaire {

    public List<Proprietaire> consulterProprietaires() throws MonException{
        return consulterProprietaires("Select * from Proprietaire;");
    }

    public Proprietaire consulterProprietaire(int id) throws MonException{
        List<Proprietaire> proprietaires = consulterProprietaires("Select * from Proprietaire where id_proprietaire = "+id+";");
        if(proprietaires.size()<1){
            throw new MonException("Il n'existe pas de propiétaire avec cet identifiant");
        }
        return proprietaires.get(0);
    }

    private List<Proprietaire> consulterProprietaires(String mysql) throws MonException {
        List<Proprietaire> mesProprietaires = new ArrayList<Proprietaire>();

        List<Object> rs;

        int index = 0;
        try {
            rs = DialogueBd.getInstance().lecture(mysql);

            while (index < rs.size()) {

                Proprietaire unP = new Proprietaire();

                unP.setIdProprietaire(Integer.parseInt(rs.get(index + 0).toString()));
                unP.setNomProprietaire(rs.get(index + 1).toString());
                unP.setPrenomProprietaire(rs.get(index + 2).toString());

                index = index + 3;
                mesProprietaires.add(unP);
            }

        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "systeme");
        }
        return mesProprietaires;
    }

    public Proprietaire insererProprietaire(Proprietaire prop){
        String rq = "insert into proprietaire (nom_proprietaire, prenom_proprietaire) values "+
                "('"+prop.getNomProprietaire()+"',"+
                "'"+ prop.getPrenomProprietaire()+"')";

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
            rs = unDialogueBd.lecture(rq);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        if(rs.size()<=0){
            return null;
        }

        prop.setIdProprietaire(Integer.parseInt(rs.get(0).toString()));

        return prop;
    }

    public Proprietaire modifierProprietaire(Proprietaire prop){

        String rq = "update proprietaire set "+
                "nom_proprietaire='"+prop.getNomProprietaire()+"',"+
                "prenom_proprietaire='"+prop.getPrenomProprietaire()+"'"+
                " where id_proprietaire="+prop.getIdProprietaire();

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.execute(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return prop;
    }

    public void supprimerProprietaire (Proprietaire prop) throws MonException {
        //Suppression des oeuvres associés
        //suppression des oeuvres associées
        new ServiceOeuvre().supprimerOeuvrepret(prop);
        new ServiceOeuvre().supprimerOeuvrevente(prop);

        String rq = "delete from proprietaire where id_proprietaire="+prop.getIdProprietaire();

        DialogueBd unDialogueBd = DialogueBd.getInstance();

        try{

            unDialogueBd.execute(rq);

        }catch(Exception ex){

            ex.printStackTrace();
            throw new MonException(ex.getMessage(), "model");
        }
    }
}
