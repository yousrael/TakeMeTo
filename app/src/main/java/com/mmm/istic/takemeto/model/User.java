package com.mmm.istic.takemeto.model;

import java.util.ArrayList;

/**
 * Created by stephen on 10/02/17.
 */

public class User {

    private String nom;
    private String prenom;
    private String mail ;
    private String phone ;
    private ArrayList<Trajet> trajetsPropose;
    private ArrayList<Trajet> reservations ;

    public User(String nom, String prenom, String mail, String phone) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.phone = phone;
    }




    public ArrayList<Trajet> getTrajetsPropose() {
        return trajetsPropose;
    }

    public void setTrajetsPropose(ArrayList<Trajet> trajetsPropose) {
        this.trajetsPropose = trajetsPropose;
    }

    public ArrayList<Trajet> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Trajet> reservations) {
        this.reservations = reservations;
    }

    public void addTrajet(Trajet trajet){
        this.trajetsPropose.add(trajet);

    }

    /**
     * ajoute un trajet aux reservation de l'utilisateur
     *
     * @param trajet le trajet
     */
    public void addReservation(Trajet trajet){
        this.reservations.add(trajet);
    }

    }

