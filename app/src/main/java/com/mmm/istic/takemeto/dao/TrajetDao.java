package com.mmm.istic.takemeto.dao;

/**
 * Created by steve on 20/03/17.
 */



import android.support.annotation.NonNull;

import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.List;
import java.util.Map;

/**
 * Implements Data access
 */
public interface TrajetDao {


    /**
     * Recherche un  trajet par clé
     * @param key une clé
     * @return un Trajet
     */
//    public List<Trajet> findAll();
    public void findUserbyKey(@NonNull SimpleCallback<Trajet> finishedCallback, String key);

    public void addPassenger(String passenger);

}
