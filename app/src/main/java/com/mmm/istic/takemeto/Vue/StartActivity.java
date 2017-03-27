package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mmm.istic.takemeto.R;

public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button signin=(Button) findViewById(R.id.button4);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,SignInActivity.class);
                startActivity(i);
              /* Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=12 square amiral andré roux&daddr=saint grégoire"));
                startActivity(intent);*/

            }
        });

        Button signup=(Button) findViewById(R.id.button3);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,FormActivity.class);
                i.putExtra("requestCode", 0);
                startActivity(i);

            }
        });


    }
}
