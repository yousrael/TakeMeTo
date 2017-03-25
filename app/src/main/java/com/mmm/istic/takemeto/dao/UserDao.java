package com.mmm.istic.takemeto.dao;



import android.support.annotation.NonNull;

import com.mmm.istic.takemeto.model.User;

import java.util.List;

/**
 * Implements Data access
 */
public interface UserDao {


    /**
     * Recherche un utilisateur selon uue adresse email
     * @param email email a rechercher
     * @return un User
     */
//    public User findUserbyEmail(String email);
    public void findUserbyEmail(@NonNull SimpleCallback<User> finishedCallback, String email);

    /**
    * Recherche la key(hashMap) d'un utilisateur selon une adresse email
    * @param email email a rechercher
    * @return une key(hashMap)
    */
//    public String findUserKeybyEmail(String email);
    public void findUserKeybyEmail(@NonNull SimpleCallback<String> finishedCallback,String email);

    /**
     * récupérer l'email de l'utilisateur courant
     * @return une adresse email
     */
    public String GetUser();
}
