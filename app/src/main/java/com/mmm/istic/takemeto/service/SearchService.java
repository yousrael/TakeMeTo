package com.mmm.istic.takemeto.service;

import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.util.Criteria;

/**
 * Created by steve on 22/03/17.
 */

 import java.util.List;

interface SearchService {

    List<Trajet> searchForTrajet(Criteria criteria);

    public List<Trajet> findAll();
}
