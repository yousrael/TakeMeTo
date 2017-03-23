package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.model.User;

public class ProfilActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button button = (Button)findViewById(R.id.button1);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra("requestCode",1);
        if(requestCode == 0){
            //Own user account
           String email= intent.getStringExtra("email");
        Log.e("get User by email", "email: "+email);
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            Query query= databaseReference.orderByChild("email").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                       User user= (User) postSnapshot.getValue();
                        Log.d("get User by email","SUCCESS"+user.getPrenom());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("get User by email","Failure");
                }
            });



            button.setEnabled(true);
        }
        else if(requestCode == 1){
            //Other user account
            button.setText("Send message");
            button.setEnabled(false);
            //TODO:Envoyer un message à un autre utilisateur
        }
    }
}
