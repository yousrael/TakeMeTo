package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.SimpleCallback;
import com.mmm.istic.takemeto.dao.UserDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class SuggestTripActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText departureDate;
    private EditText arrivalDate;
    private EditText departure;
    private EditText arrival;
    private EditText places;
    private EditText prixTrajet;
    private EditText heureDepart;
    private EditText heureArrivee;



    //For the DatePicker dialog
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dated;
    DatePickerDialog.OnDateSetListener datea;
    boolean datedb=false;
    boolean dateab=false;

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

        Button create = (Button) findViewById(R.id.button7);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTrajet(); //Adding new trajet on the database
                Intent i = new Intent(SuggestTripActivity.this, MyListTripsActivity.class);
                startActivity(i);

            }
        });

        //Dialog to select a date, see also updateDateDeNaissance() at the bottom
        datea = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateArrival();
            }

        };
        arrivalDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SuggestTripActivity.this, datea, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dated = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDatedeparture();
            }

        };
        departureDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SuggestTripActivity.this, dated, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
      /*  arrivalDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!dateab) {
                    new DatePickerDialog(SuggestTripActivity.this, datea, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                dateab = !dateab;
            }
        });


        departureDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!datedb) {

                    new DatePickerDialog(SuggestTripActivity.this, dated, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                datedb = !datedb;
            }
        });*/
        //end date dialog

    }

    private void addNewTrajet() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        UserDaoImpl serviceUser = new UserDaoImpl();

        serviceUser.findUserKeybyEmail(new SimpleCallback<String>() {
            @Override
            public void callback(String data) {
                if (data != null) {
                    Map<String, Object> voyageurs = new HashMap<>();
                    String keyvoyageur = databaseReference.child("vayageurs").push().getKey();
                    voyageurs.put(keyvoyageur, "-KfobKb7oMRm1JMQvl8L");
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(new Trajet(data,
                            departureDate.getText().toString() + "_" + departure.getText().toString() + "_" + arrival.getText().toString(),
                            arrivalDate.getText().toString(),
                            departure.getText().toString(),
                            arrival.getText().toString(),
                            Integer.valueOf(places.getText().toString()),
                            Integer.valueOf(prixTrajet.getText().toString()),
                            voyageurs));
                } else {
                    // error
                }
            }

        },email);

    }

    //Updating the after selectining it from the dialog
    private void updateDatedeparture() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        departureDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateDateArrival() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        arrivalDate.setText(sdf.format(myCalendar.getTime()));
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
            case R.id.home:
                goBackHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBackHome() {
        //  FirebaseAuth.AuthStateListener mAuthListener;
        final Intent i;
        i = new Intent(SuggestTripActivity.this, HomeActivity.class);
        startActivity(i);
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
