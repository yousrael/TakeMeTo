package com.mmm.istic.takemeto.model;

/**
 * Created by steve on 18/03/17.
 */

import android.location.Address;

import java.sql.Date;

import java.util.List;

/**
Classe representant un trajet de l'application
 */
public class Trajet {

    private String user ;
    private java.util.Date dateDepart ;
    private Address addressDepart;
    private Address addressArrivee;
    private int prixTrajet;
    private List<String> vayageurs ;

    public Trajet(String user, java.util.Date dateDepart, Address addressdepart, Address addressarrive, int prixTrajet, int places) {
        this.user = user;
        this.dateDepart = dateDepart;
        this.addressDepart = addressdepart;
        this.addressArrivee = addressarrive;
        this.prixTrajet = prixTrajet;
        this.places = places;

    }



    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public void setDateDepart(java.util.Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    private int places ;

    public List<String> getVayageurs() {
        return vayageurs;
    }

    public void setVayageurs(List<String> vayageurs) {
        this.vayageurs = vayageurs;
    }



    public Address getAddressdepart() {
        return addressDepart;
    }

    public void setAddressdepart(Address addressdepart) {
        this.addressDepart = addressdepart;
    }

    public Address getAddressarrive() {
        return addressArrivee;
    }

    public void setAddressarrive(Address addressarrive) {
        this.addressArrivee = addressarrive;
    }




    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public java.util.Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public int getPrixTrajet() {
        return prixTrajet;
    }

    public void setPrixTrajet(int prixTrajet) {
        this.prixTrajet = prixTrajet;
    }

    /**
     * Ajoute un vayageur au trajet
     * @param user l'identifinat de l'utilisateur
     * @return true si l'utilisateur a été ajouté faux si non
     */
    public boolean addVoyageur (String user){
        boolean res = nombresPlacesDisponible()>0;
        if(res){
            this.vayageurs.add(user);
        }
       return res;

    }

    /**
     * Retourne le nombre de places disponible sur le trajet
     * @return un nombre positif de places disponibles 0 si il ne reste pas de place
     */
    public int nombresPlacesDisponible(){
        if (this.vayageurs.size() < this.places){
            return this.vayageurs.size()- this.places ;
        }
        else {
            return 0;
        }


    }


}
