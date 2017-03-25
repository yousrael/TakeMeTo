package com.mmm.istic.takemeto.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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


    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    public void putTrajet(Trajet trajet) {
       DatabaseReference trajets= this.database.child("trajets/");
        String key= trajets.push().getKey();
        trajets.child(key).setValue(trajet);


    }

    @Override
    public List<Trajet> findTrajetbyCriteria(Criteria criteria) {

        final String arrival =criteria.getArrival();
        final String date = criteria.getDepartureDate();
        final List<Trajet> searchedTrajets = new ArrayList<Trajet>();
        final ValueEventListener trajetListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> trajets = dataSnapshot.getChildren();
                if (trajets != null){
                    for(DataSnapshot child :trajets){
                        Trajet trajet = (Trajet) child.getValue();
                        searchedTrajets.add(trajet);
                    }
                }
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

        database.child("trajets/").equalTo(criteria.getDeparture(),"departure").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().equalTo(arrival,"arrival").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().equalTo(date,"departureDate").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> trajets = dataSnapshot.getChildren();
                                if (trajets != null){
                                    for(DataSnapshot child :trajets){
                                        Trajet trajet = (Trajet) child.getValue();
                                        searchedTrajets.add(trajet);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("errorCriteriaDate",databaseError.getDetails());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.e("Searched Trajet",searchedTrajets.toString()+searchedTrajets.size());
        return searchedTrajets;

    }

    /**
     * Find all the trips in base
     * @return a list of those trips if there is any, an empty list if not
     */
    @Override
    public List<Trajet> findAll(){
        final List<Trajet> searchedTrajets = new ArrayList<Trajet>();
        database.child("trajets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> trajets = dataSnapshot.getChildren();
                if (trajets != null){
                    for(DataSnapshot childTrajet :trajets){
                        Log.e("child snapshot " ,""+childTrajet.toString());
                        Trajet trajet =childTrajet.getValue(Trajet.class);
                        Log.e("Trajet",trajet.getDeparture());
                        searchedTrajets.add(trajet);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
 return searchedTrajets;
    }


}
