package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.TrajetDao;
import com.mmm.istic.takemeto.dao.TrajetDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    //To get the criteria filter via intent from the previous activity
    private String departureDate;
    private String arrivalDate;
    private String departure;
    private String arrival;

    //Firebase database
    private DatabaseReference databaseReference;

    //DAO filter
    TrajetDao trajetDao;
    Criteria criteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        departureDate = intent.getStringExtra("departureDate");
        arrivalDate = intent.getStringExtra("arrivalDate");
        departure = intent.getStringExtra("departure");
        arrival = intent.getStringExtra("arrival");

        /*Log.d("depart date", departureDate);
        Log.d("arrival date", arrivalDate);
        Log.d("depart place", departure);
        Log.d("arrival place", arrival);*/

        /*trajetDao = new TrajetDaoImpl();
        criteria = new Criteria(departureDate+"_"+departure+"_"+arrival, departure, arrival);

        List<Trajet> trajets = trajetDao.findTrajetbyCriteria(criteria);*/
        /*Log.d("La liste des trajets", trajets.toString());
        Log.d("taille de la liste", String.valueOf(trajetDao.findTrajetbyCriteria(criteria).size()));*/


        databaseReference = FirebaseDatabase.getInstance().getReference("trajets");
        Query query = databaseReference.orderByChild("departureDate").equalTo(departureDate+"_"+departure+"_"+arrival);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Map<String, Trajet> trajets = new HashMap<String, Trajet>();
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        Trajet trajet = userSnapshot.getValue(Trajet.class);
                        Log.d("current trajet key :", userSnapshot.getKey());
                        trajets.put(userSnapshot.getKey(), trajet);
                    }

                    ArrayList<Trajet> values = new ArrayList<>(trajets.values());
                    List<String> keys = new ArrayList<String>(trajets.keySet());
                    for (Trajet trajet : values) {
                        Log.d("firebase arrival date ", trajet.getArrivalDate());
                        Log.d("firebase available sits", String.valueOf(trajet.getPlaces()));
                        Log.d("firebase trajet price ", String.valueOf(trajet.getPrixTrajet()));
                    }
                }
                else {
                    Log.e("null user","null user");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("get User by email","Failure");
            }
        });


    }
}
