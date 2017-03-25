package com.mmm.istic.takemeto.Vue;

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

import com.google.firebase.auth.FirebaseAuth;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.service.SearchServiceImpl;
import com.mmm.istic.takemeto.util.Criteria;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchTripActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth ;
    Intent intent = getIntent();
    SearchServiceImpl searchService = new SearchServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);
        Button search=(Button) findViewById(R.id.button_searchTrip);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText departureDate= (EditText) findViewById(R.id.edit_departureDate) ;
                EditText departure = (EditText) findViewById(R.id.edit_departure);
                EditText arrival = (EditText) findViewById(R.id.edit_arrival);
                Criteria criteria  = new Criteria(departure.getText().toString(),arrival.getText().toString(),departureDate.getText().toString());
                List<Trajet> trajets =searchService.searchForTrajet(criteria);
                Trajet[] parcelableTrajets= new Trajet[trajets.size()] ;
                trajets.toArray(parcelableTrajets);
                Intent intent=new Intent(SearchTripActivity.this,SearchResultActivity.class);
                intent.putExtra("trajets",parcelableTrajets);
                startActivity(intent);
            }
        });
    }

}
