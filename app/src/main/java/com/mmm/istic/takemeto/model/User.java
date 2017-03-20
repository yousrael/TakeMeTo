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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

