package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestTripActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText departureDate;
    private EditText arrivalDate;
    private EditText departure;
    private EditText arrival;
    private EditText places ;
    private EditText prixTrajet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_trip);

        departureDate = (EditText) findViewById(R.id.edit_suggest_trip_date_depart);
        arrivalDate = (EditText) findViewById(R.id.edit_suggest_trip_date_arrivee);
        departure = (EditText) findViewById(R.id.edit_suggest_trip_depart_place);
        arrival = (EditText) findViewById(R.id.edit_suggest_trip_arrival_place);
        places = (EditText) findViewById(R.id.edit_suggest_trip_sits);
        prixTrajet = (EditText) findViewById(R.id.edit_suggest_trip_price);

        //Firebase database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("trajets");

        Button create=(Button) findViewById(R.id.button7);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTrajet(); //Adding new trajet on the database
                Intent i=new Intent(SuggestTripActivity.this,MyListTripsActivity.class);
                startActivity(i);

            }
        });
    }

    private void addNewTrajet() {

        Map<String, String> voyageurs = new HashMap<>();
        String key = databaseReference.push().getKey(); //generating a key
        databaseReference.child(key).setValue(new Trajet("KfobKb7oMRm1JMQvl8L", departureDate.getText().toString(),
                arrivalDate.getText().toString(),
                departure.getText().toString(),
                arrival.getText().toString(),
                Integer.valueOf(places.getText().toString()),
                Integer.valueOf(prixTrajet.getText().toString()),
                voyageurs));
    }
}
