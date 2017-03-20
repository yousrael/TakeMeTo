package com.mmm.istic.takemeto.model;

/**
 * Created by steve on 18/03/17.
 */

import java.sql.Date;

/**
Classe representant un trajet de l'application
 */
public class Trajet {

    private User user ;
    private Date dateDepart ;
    private String lieuDepart;
    private String lieuArrivee;
    private int prixTrajet;

    public Trajet(User user, Date dateDepart, String lieuDepart, String lieuArrivee, int prixTrajet) {
        this.user = user;
        this.dateDepart = dateDepart;
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.prixTrajet = prixTrajet;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(String lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public String getLieuArrivee() {
        return lieuArrivee;
    }

    public void setLieuArrivee(String lieuArrivee) {
        this.lieuArrivee = lieuArrivee;
    }

    public int getPrixTrajet() {
        return prixTrajet;
    }

    public void setPrixTrajet(int prixTrajet) {
        this.prixTrajet = prixTrajet;
    }






}
