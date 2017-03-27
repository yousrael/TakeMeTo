package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.SimpleCallback;
import com.mmm.istic.takemeto.dao.UserDao;
import com.mmm.istic.takemeto.dao.UserDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    String keyTripUser;
    String emailCurrentUser;
    FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
    String currentUsermail = fireBaseAuth.getCurrentUser().getEmail();
    //Firebase database
    private DatabaseReference databaseReference;

    UserDao usrDao = new UserDaoImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Intent intent = getIntent();


        TextView t_numero = (TextView) findViewById(R.id.t_numero);
        TextView t_nbplacelibre = (TextView) findViewById(R.id.t_nbplacelibre);
        final TextView t_nom_prenom = (TextView) findViewById(R.id.t_nom_prenom);
        TextView t_prix = (TextView) findViewById(R.id.t_prix);
        TextView t_arrival_date = (TextView) findViewById(R.id.t_arrival_date);
        TextView t_arrival_lieu = (TextView) findViewById(R.id.t_arrival_lieu);
        TextView t_depart_date = (TextView) findViewById(R.id.t_depart_date);
        TextView t_depart_lieu = (TextView) findViewById(R.id.t_depart_lieu);

        //get the 2 eamil

        UserDaoImpl serviceUser = new UserDaoImpl();
        emailCurrentUser = serviceUser.GetUser();
        emailTripUser = intent.getStringExtra("emailUser");
        Log.e("emailUser", "emailUser" + emailTripUser);
        if (emailTripUser == null) {
            Log.e("null emailUser", "loadinf keyUser");
            keyTripUser = intent.getStringExtra("keyUser");

            serviceUser.findUserbyKey(new SimpleCallback<User>() {
                @Override
                public void callback(User data) {
                    emailTripUser = data.getMail();
                    Log.e("Setting emailTripUser", "" + emailTripUser);
                    t_nom_prenom.setText(emailTripUser);
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
                }
            }, "" + keyTripUser);
            Log.e("null emailUser", "loadinf keyUser / " + keyTripUser);
        } else {
            t_nom_prenom.setText(emailCurrentUser);
        }

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
        int aze = Integer.parseInt("" + intent.getIntExtra("places", 0));
        trajet.setPlaces(aze);
        int ert = Integer.parseInt("" + intent.getIntExtra("prixTrajet", 0));
        trajet.setPrixTrajet(ert);


        t_numero.setText("" + trajet.hashCode());
        t_nbplacelibre.setText("" + trajet.getPlaces());
        t_prix.setText("" + trajet.getPrixTrajet() + "€");
        t_arrival_date.setText(trajet.getArrivalDate());
        t_arrival_lieu.setText(trajet.getArrival());
        t_depart_date.setText(trajet.getDepartureDate().split("_")[0]);
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
                    Intent intent2 = getIntent();
                    String key = intent2.getStringExtra("key");
                    //Add the current user to trip passanger list

                    final String[] currentUserKey = new String[1];
                    databaseReference = FirebaseDatabase.getInstance().getReference("trajets/" + key + "/vayageurs");

                    Log.e("keyAAA---------------->", key);


                    usrDao.findUserKeybyEmail(new SimpleCallback<String>() {
                        @Override
                        public void callback(String data) {
                            if (data != null) {
                                String keyUser = data;
                                //Add the current user to trip passanger list
                                Map<String, Object> vayageurs = new HashMap<>();
                                String keyVoyageur = databaseReference.push().getKey();
                                vayageurs.put(keyVoyageur, keyUser);
                                databaseReference.updateChildren(vayageurs);
                            } else {
                                Log.e("Error", "Error in booking trip");
                            }
                        }
                    }, currentUsermail);

                    //Start the next activity
                    Intent intent = new Intent(TripActivity.this, HomeActivity.class);
                    Context context = getApplicationContext();
                    CharSequence text = "Trip Booked";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.trips:
                getTrips();
                return true;
            case R.id.profil:
                affichProfil();
                return true;
            case R.id.suggestions:
                suggestions();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void suggestions() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "TakeMeToTeam@gmail.com" }); // email id can be hardcoded too
        try {
            startActivity(Intent.createChooser(emailIntent, "Done!"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No Email client found!!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void affichProfil() {
        //  FirebaseAuth.AuthStateListener mAuthListener;
        final Intent i;
        i = new Intent(this, ProfilActivity.class);
        i.putExtra("requestCode", 0);
        startActivity(i);

    }




    private void getTrips() {
        Intent i=new Intent(this,MyListTripsActivity.class);
        startActivity(i);

    }

}
