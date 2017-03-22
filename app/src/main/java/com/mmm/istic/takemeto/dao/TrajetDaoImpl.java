package com.mmm.istic.takemeto.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by steve on 20/03/17.
 */

/**
 * A data Access Object for trips
 */
public class TrajetDaoImpl implements TrajetDao {


    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("trajets");


    @Override
    public void putTrajet(Trajet trajet) {

        this.database.setValue(trajet);

    }

    @Override
    public List<Trajet> findTrajetbyCriteria(Criteria criteria) {

final List<Trajet> searchedTrajets = new ArrayList<Trajet>();
        ValueEventListener trajetListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Iterable<DataSnapshot> trajets = dataSnapshot.getChildren();
                if (trajets != null){
                    for(DataSnapshot child :trajets){
                        Trajet trajet = (Trajet) child.getValue();
                        searchedTrajets.add(trajet);

                    }
                }
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "search for trajet :onCancelled", databaseError.toException());
                // ...
            }
        };



        database.equalTo(criteria.getDeparture(),"departure")
                .equalTo(criteria.getArrival(),"arrival").
                equalTo(criteria.getDepartureDate(),"departureDate").addListenerForSingleValueEvent(trajetListener);

        return searchedTrajets;

    }
}
