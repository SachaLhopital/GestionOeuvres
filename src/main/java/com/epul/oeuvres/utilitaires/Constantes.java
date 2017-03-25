package com.epul.oeuvres.utilitaires;

/**
 * Fichier de constantes
 * Created by Sachouw on 21/02/2017.
 */
public class Constantes {

    /* Erreurs */
    public static final String ERROR_KEY = "messageErreur";
    public static final String ERROR_PAGE = "Erreur";
    public static final String ERROR_ID_MISSING = "Id manquant";
    public static final String ERROR_MISSING_ARGS = "Informations manquantes";
    public static final String ERROR_UPDATING = "Impossible de mettre à jour";
    public static final String ERROR_DELETING = "Erreur lors de la suppression";
    public static final String ERROR_LISTING = "Impossible de récupérer la liste des %ss";
    public static final String ERROR_INSERT = "Impossible d'insérer un %s";
    public static final String ERROR_DETAIL = "Impossible d'obtenir les détails pour un %s";

    /* Etats d'un oeuvre de vente */
    public enum EtatsOeuvre {
        L,
        R;
    }
}
