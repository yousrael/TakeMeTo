package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mmm.istic.takemeto.R;

public class FormActivity extends AppCompatActivity {


    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //intent.putExtra("requestCode", requestCode)
        //startActivityForResult(intent, NEW_CLIENT);
        Button button = (Button)findViewById(R.id.CreateNewUser);
        TextView textview1 = (TextView)findViewById(R.id.textView6);
        TextView textview2 = (TextView)findViewById(R.id.textView7);
        if(intent.getIntExtra("requestCode",0) == 0){
            //New user creation
            button.setText("Create new user");
            textview1.setText("Register Form");
            textview1.setText(" please answer all the input");
        }
        else if(intent.getIntExtra("requestCode",1) == 1){
            //Modify existing user
            button.setText("Apply modification");
            textview1.setText("Modification Form");
            textview1.setText(" please answer all the input");
        }
    }

    protected  void onClickFunction(){
        if(intent.getIntExtra("requestCode",0) == 0){
            //New user creation
        }
        else if(intent.getIntExtra("requestCode",1) == 1){
            //Modify existing user

        }
    }
}
