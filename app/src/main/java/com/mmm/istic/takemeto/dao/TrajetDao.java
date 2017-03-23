package com.mmm.istic.takemeto.dao;

/**
 * Created by steve on 20/03/17.
 */



import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.List;
import java.util.Map;

/**
 * Implements Data access
 */
public interface TrajetDao {

    /**
     * enregistre un trajet dans la base
     * @param trajet
     */
    public void putTrajet (Trajet trajet);

    /**
     * Recherche des trajet selon un critère donné
     * @param criteria critère de recherche
     * @return Une map de trajet
     */
    public List<Trajet> findTrajetbyCriteria(Criteria criteria);
}
