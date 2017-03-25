package com.epul.oeuvres.dao;

import com.epul.oeuvres.meserreurs.MonException;
import com.epul.oeuvres.metier.Adherent;
import com.epul.oeuvres.persistance.DialogueBd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachouw on 11/03/2017.
 */
public class ServiceAdherent {

    // gestion des adherents
    // Consultation d'un adh�rent par son num�ro
    // Fabrique et renvoie un objet adh�rent contenant le r�sultat de la requ�te
    // BDD
    public Adherent consulterAdherent(int numero) throws MonException {
        String mysql = "select * from adherent where id_adherent=" + numero;
        List<Adherent> mesAdh = consulterListeAdherents(mysql);
        if (mesAdh.isEmpty())
            return null;
        else {
            return mesAdh.get(0);
        }
    }

    // Consultation des adh�rents
    // Fabrique et renvoie une liste d'objets adh�rent contenant le r�sultat de
    // la requ�te BDD
    public List<Adherent> consulterListeAdherents() throws MonException {
        String mysql = "select * from adherent";
        return consulterListeAdherents(mysql);
    }


    private List<Adherent> consulterListeAdherents(String mysql) throws MonException {
        List<Object> rs;
        List<Adherent> mesAdherents = new ArrayList<>();
        int index = 0;
        try {
            DialogueBd unDialogueBd = DialogueBd.getInstance();
            rs = unDialogueBd.lecture(mysql);
            while (index < rs.size()) {
                // On cr�e un stage
                Adherent unA = new Adherent();
                // il faut redecouper la liste pour retrouver les lignes
                unA.setIdAdherent(Integer.parseInt(rs.get(index + 0).toString()));
                unA.setNomAdherent(rs.get(index + 1).toString());
                unA.setPrenomAdherent(rs.get(index + 2).toString());
                unA.setVilleAdherent(rs.get(index + 3).toString());
                // On incr�mente tous les 3 champs
                index = index + 4;
                mesAdherents.add(unA);
            }

            return mesAdherents;
        } catch (Exception exc) {
            throw new MonException(exc.getMessage(), "systeme");
        }
    }

    public void supprimerAdherent (Adherent adherent){
        String rq = "delete from adherent where id_adherent ="+adherent.getIdAdherent()+";";

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.execute(rq);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        //suppression des reservations de l'adherent
        new ServiceReservation().supprimerReservation(adherent);
    }

    public Adherent modifierAdherent(Adherent adherent){
        String rq = "update adherent set nom_adherent='"+adherent.getNomAdherent()+"'"+
                ", prenom_adherent ='"+adherent.getPrenomAdherent()+"'"+
                ", ville_adherent ='"+adherent.getVilleAdherent()+"'"+
                " where id_adherent="+adherent.getIdAdherent();

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.execute(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return adherent;
    }

    public Adherent insertAdherent(Adherent adherent){
        String rq = "insert into adherent (nom_adherent, prenom_adherent, ville_adherent) values ('"+
                adherent.getNomAdherent()+"','"+adherent.getPrenomAdherent()+"','"+adherent.getVilleAdherent()+"')";

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

        adherent.setIdAdherent(Integer.parseInt(rs.get(0).toString()));

        return adherent;

    }
}