package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mmm.istic.takemeto.R;

public class TripActivity extends AppCompatActivity {

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Button button = (Button)findViewById(R.id.button9);

        if(intent.getIntExtra("requestCode",0) == 0){
            //Own user account
            button.setEnabled(true);
        }
        else if(intent.getIntExtra("requestCode",1) == 1){
            //Other user account
            button.setText("Send message");
            button.setEnabled(false);
            //TODO:Envoyer un message à un autre utilisateur
        }

    }
}
