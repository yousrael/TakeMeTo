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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
               // Iterable<DataSnapshot> trajets = dataSnapshot.getChildren();
                //Map<String, Trajet> trajets = new HashMap<String, Trajet>();
                if (dataSnapshot.getValue() != null) {
                    System.out.println("Bonjour");
                   Log.d("les trajets", "les trajets : ");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Trajet trajet = child.getValue(Trajet.class);
                        Log.d("trajet depart", trajet.getDeparture());
                        Log.d("trajet arrivee", trajet.getArrival());
                        Log.d("trajet place", String.valueOf(trajet.getPlaces()));
                        Log.d("trajet prix", String.valueOf(trajet.getPrixTrajet()));
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

        Log.d("departure date ", criteria.getDepartureDate());
        database.equalTo(criteria.getDepartureDate(), "departureDate").addListenerForSingleValueEvent(trajetListener);
                //.equalTo(criteria.getArrival(), "arrival").
               // equalTo(criteria.getDepartureDate(), "departureDate").addListenerForSingleValueEvent(trajetListener);

        return searchedTrajets;

    }
}
