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
        return consulterProprietaires("Select * from Proprietaire;");
    }

    public Proprietaire consulterProprietaire(int id){
        List<Proprietaire> proprietaires = consulterProprietaires("Select * from Proprietaire where id_proprietaire = "+id+";");
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
            rs = unDialogueBd.lecture(mysql);

            while (index < rs.size()) {

                Proprietaire unP = new Proprietaire();

                unP.setIdProprietaire(Integer.parseInt(rs.get(index + 0).toString()));
                unP.setNomProprietaire(rs.get(index + 1).toString());
                unP.setPrenomProprietaire(rs.get(index + 2).toString());

                index = index + 3;
                mesProprietaires.add(unP);
            }

        } catch (Exception exc) {
            try {
                throw new MonException(exc.getMessage(), "systeme");
            } catch (MonException e) {
                e.printStackTrace();
            }
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
            rs = DialogueBd.lecture(rq);
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

    public void supprimerProprietaire (Proprietaire prop){
        String rq = "delete from proprietaire where id_proprietaire="+prop.getIdProprietaire();

        DialogueBd unDialogueBd = DialogueBd.getInstance();
        try{
            unDialogueBd.execute(rq);
        }catch(Exception ex){
            ex.printStackTrace();
            return;
        }

        //suppression des oeuvres associées
        new ServiceOeuvre().supprimerOeuvrepret(prop);
        new ServiceOeuvre().supprimerOeuvrevente(prop);
    }
}
