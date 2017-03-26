package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.SimpleCallback;
import com.mmm.istic.takemeto.dao.UserDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * afficher les informations d'un trajet
 *
 * @intent.putExtra("departure",departure);
 * @intent.putExtra("arrival",arrival);
 * @intent.putExtra("departureDate",departureDate);
 * @intent.putExtra("arrivalDate",arrivalDate);
 * @intent.putExtra("places",places);
 * @intent.putExtra("prixTrajet",prixTrajet);
 * @intent.putExtra("emailUser",email);
 */

public class TripActivity extends AppCompatActivity {


    String emailTripUser;
    String emailCurrentUser;

    //Firebase database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Intent intent = getIntent();
        //get the 2 eamil

        UserDaoImpl serviceUser = new UserDaoImpl();
        emailCurrentUser = serviceUser.GetUser();
        emailTripUser = intent.getStringExtra("emailUser");

        //set listener sur le nom pour afficher le profile
        TextView showUser = (TextView) findViewById(R.id.t_nom_prenom);
        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TripActivity.this, ProfilActivity.class);
                if (emailCurrentUser.equals(emailTripUser)) {
                    //Own user account
                    i.putExtra("requestCode", 0);

                } else {
                    //Other user account
                    i.putExtra("requestCode", 1);
                    i.putExtra("email", emailTripUser);
                }
                startActivity(i);

            }

        });

        Button button = (Button) findViewById(R.id.t_bouton);
        final Trajet trajet = new Trajet();
        trajet.setDeparture(intent.getStringExtra("departure"));
        trajet.setArrival(intent.getStringExtra("arrival"));
        trajet.setDepartureDate(intent.getStringExtra("departureDate"));
        trajet.setArrivalDate(intent.getStringExtra("arrivalDate"));
        trajet.setPlaces(intent.getIntExtra("places", 0));
        trajet.setPrixTrajet(intent.getIntExtra("prixTrajet", 0));


        TextView t_numero = (TextView) findViewById(R.id.t_numero);
        TextView t_nbplacelibre = (TextView) findViewById(R.id.t_nbplacelibre);
        final TextView t_nom_prenom = (TextView) findViewById(R.id.t_nom_prenom);
        TextView t_prix = (TextView) findViewById(R.id.t_prix);
        TextView t_arrival_date = (TextView) findViewById(R.id.t_arrival_date);
        TextView t_arrival_lieu = (TextView) findViewById(R.id.t_arrival_lieu);
        TextView t_depart_date = (TextView) findViewById(R.id.t_depart_date);
        TextView t_depart_lieu = (TextView) findViewById(R.id.t_depart_lieu);

        t_numero.setText("" + trajet.hashCode());
        t_nbplacelibre.setText("" + trajet.getPlaces());
        t_prix.setText("" + trajet.getPrixTrajet() + "€");
        t_arrival_date.setText(trajet.getArrivalDate());
        t_arrival_lieu.setText(trajet.getArrival());
        t_depart_date.setText(trajet.getDepartureDate());
        t_depart_lieu.setText(trajet.getDeparture());

        serviceUser.findUserbyEmail(new SimpleCallback<User>() {
            @Override
            public void callback(User data) {
                t_nom_prenom.setText("" + data.getPrenom() + " " + data.getNom());
            }
        }, emailTripUser);

        if (emailCurrentUser.equals(emailTripUser)) {
            //Own user account
            button.setEnabled(true);
            button.setText("Edit trip");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:edit un trip
                    Log.e("qsdqsd", "CLICK edit");
                    //Add the current user to trip passanger list
                    Map<String, Object> vayageurs = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference("trajets/-Kg4Pwto2HoNaSaKNOmM/vayageurs");
                    String keyVoyageur = databaseReference.push().getKey();
                    vayageurs.put(keyVoyageur, "current user key");
                    databaseReference.updateChildren(vayageurs);

                    //Start the next activity
                    Intent intent = new Intent(TripActivity.this, SuggestTripActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            //Other user account
            button.setEnabled(true);
            button.setText("book this trip");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:Book un trip
                    Log.e("qsdqsd", "CLICK book");
                    Intent intent = new Intent(TripActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        }

        Button buttonGoogle = (Button) findViewById(R.id.t_google);
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qsdqsd", "CLICK MAP!!!");
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + trajet.getDeparture() + "&daddr=" + trajet.getArrival()));
                startActivity(intent);
            }
        });


    }

}
