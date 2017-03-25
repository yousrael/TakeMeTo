package com.mmm.istic.takemeto.model;

/**
 * Created by steve on 18/03/17.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
Classe representant un trajet de l'application
 */
public class Trajet {

    private String user ;
    private String departureDate;
    private String arrivalDate;
    private String departure;
    private String arrival;
    private Integer places ;
    private Integer prixTrajet;
    private ArrayList<String> vayageurs ;

    public Trajet(String user, String departureDate, String arrivalDate, String departure, String arrival,
                  Integer places, Integer prixTrajet, ArrayList<String> vayageurs){
        this.user = user;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departure = departure;
        this.arrival = arrival;
        this.places = places;
        this.prixTrajet = prixTrajet;
        this.vayageurs = vayageurs;

    }




    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public ArrayList<String> getVayageurs() {
        return vayageurs;
    }

    public void setVayageurs(ArrayList<String> vayageurs) {
        this.vayageurs = vayageurs;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
   /* public boolean addVoyageur (String user){
        boolean res = nombresPlacesDisponible()>0;
        if(res){
            this.vayageurs.add(user);
        }
       return res;

    }*/

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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}
