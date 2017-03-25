package com.mmm.istic.takemeto.service;

import com.mmm.istic.takemeto.dao.TrajetDao;
import com.mmm.istic.takemeto.dao.TrajetDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.List;

/**
 * Created by steve on 22/03/17.
 */

public class SearchServiceImpl implements SearchService {

    private TrajetDao trajeDao;

    public SearchServiceImpl(TrajetDao trajeDao) {
        this.trajeDao = trajeDao;
    }

    public SearchServiceImpl() {
        this.trajeDao= new TrajetDaoImpl();
    }

    @Override
    public List<Trajet> searchForTrajet(Criteria criteria) {

       return this.trajeDao.findTrajetbyCriteria(criteria);
    }


    @Override
    public List<Trajet> findAll(){
        return this.trajeDao.findAll();
    }

}
