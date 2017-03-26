package com.mmm.istic.takemeto.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by steven on 20/03/17.
 */

/**
 * A data Access Object for trips
 */
public class TrajetDaoImpl implements TrajetDao {

    private DatabaseReference databaseReference ;

    private FirebaseAuth firebaseAuth;
    private ArrayList<Trajet> foundTrajets;

    @Override
    public void findUserbyKey(@NonNull final SimpleCallback<Trajet> finishedCallback, String key) {
        databaseReference = FirebaseDatabase.getInstance().getReference("trajets");
        Query query = databaseReference.orderByKey().equalTo(key);
        Log.e("db ref",databaseReference.toString());

        ArrayList<Trajet> trajet = null;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /// for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                if(dataSnapshot.getValue() != null){
                    Map<String, Trajet> trajets = new HashMap<String, Trajet>();
                    for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                        Trajet trajet = jobSnapshot.getValue(Trajet.class);
                        trajets.put(jobSnapshot.getKey(), trajet);
                    }

                    foundTrajets = new ArrayList<>(trajets.values());
                    List<String> keys = new ArrayList<String>(trajets.keySet());
                    for (Trajet trajet: foundTrajets) {
                        Log.d("firebase trajet find: ",trajet.getDeparture()+" "+trajet.getArrival());
                    }
                    if(foundTrajets.size() ==1)
                        finishedCallback.callback(foundTrajets.get(0));
                    else
                        finishedCallback.callback(null);
                        /*Log.d("class name of user ", dataSnapshot.getValue().getClass().getName());
                        Log.d("real user ", dataSnapshot.getValue(User.class).getClass().getName());
                        Log.e("get User by email","SUCCESS"+dataSnapshot.getValue(User.class));*/
                    //Log.e("get User by email","SUCCESS"+user.getNom());
                }
                else {
                    Log.e("null trajet","null trajet");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("get Trajet by key","Failure");
            }
        });
    }

    @Override
    public void addPassenger(String passenger) {
        databaseReference = FirebaseDatabase.getInstance().getReference("voyageurs");
        databaseReference.setValue("");
    }

}
