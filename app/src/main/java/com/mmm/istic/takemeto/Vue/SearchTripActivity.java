package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.util.Criteria;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchTripActivity extends AppCompatActivity {

    private EditText departureDate;
    private EditText arrivalDate;
    private EditText departure;
    private EditText arrival;

    FirebaseAuth firebaseAuth ;
    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        departureDate = (EditText) findViewById(R.id.edit_search_trip_date_depart);
        arrivalDate = (EditText) findViewById(R.id.edit_search_trip_date_arrivee);
        departure = (EditText) findViewById(R.id.edit_search_trip_depart_place);
        arrival = (EditText) findViewById(R.id.edit_search_trip_arrival_place);

        Button search=(Button) findViewById(R.id.button_search_validate_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchTripActivity.this, SearchResultActivity.class);
                i.putExtra("departureDate", departureDate.getText().toString());
                i.putExtra("arrivalDate", arrivalDate.getText().toString());
                i.putExtra("departure", departure.getText().toString());
                i.putExtra("arrival", arrival.getText().toString());
                startActivity(i);

            }
        });
    }

}
