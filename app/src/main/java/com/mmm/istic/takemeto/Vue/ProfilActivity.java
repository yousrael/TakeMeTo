package com.mmm.istic.takemeto.Vue;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.SimpleCallback;
import com.mmm.istic.takemeto.dao.UserDaoImpl;
import com.mmm.istic.takemeto.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;

/**
 * Affiche le profile de l'utilisateur courrant
 * @intent requestCode == 0
 *
 * Affiche les profile des autres utilisateurs*
 * @intent requestCode == 1
 * @intent email = "xx@xx.com"
 *
 **/
public class ProfilActivity extends AppCompatActivity {


    TextView p_nom ;
    TextView p_prenom;
    TextView p_email;
    TextView p_birthdate;
    TextView p_phone;
    ImageView image;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String email;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
         p_nom = (TextView) findViewById(R.id.p_nom);
         p_prenom  = (TextView) findViewById(R.id.p_prenom);
         p_email = (TextView) findViewById(R.id.p_email);
         p_birthdate  = (TextView) findViewById(R.id.p_birthdate);
         p_phone  = (TextView) findViewById(R.id.p_phone);
        image= (ImageView) findViewById(R.id.imageView);


        Button button = (Button)findViewById(R.id.button1);
        button.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        int requestCode = intent.getIntExtra("requestCode",1);
        if(requestCode == 0) {
            Log.e("Profile","logged user");
            //Own user account


            UserDaoImpl serviceUser = new UserDaoImpl();
            String email = serviceUser.GetUser();
            serviceUser.findUserbyEmail(new SimpleCallback<User>() {
                @Override
                public void callback(User data) {
                    if (data != null) {
                        byte [] encodeByte=Base64.decode(data.getImage(),Base64.DEFAULT);
                        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    p_nom.setText(data.getNom());
                        p_prenom.setText(data.getPrenom());
                        p_birthdate.setText(data.getDateDeNaissance());
                        p_email.setText(data.getMail());
                        p_phone.setText(data.getPhone());
                        image.setImageBitmap(bitmap);

                    } else {
                        // error
                    }
                }
            }, email);


        }

        else if(requestCode == 1){
            Log.e("Profile","other user");
            //Other user account
            button.setText("Send message");
            button.setEnabled(false);
            //TODO:Envoyer un message à un autre utilisateur


            String profileEmail = intent.getStringExtra("email");
            if(email != null && !email.isEmpty()) {
                UserDaoImpl serviceUser = new UserDaoImpl();
                serviceUser.findUserbyEmail(new SimpleCallback<User>() {
                    @Override
                    public void callback(User data) {
                        if (data != null) {
                            byte [] encodeByte=Base64.decode(data.getImage(),Base64.DEFAULT);
                            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            p_nom.setText(data.getNom());
                            p_prenom.setText(data.getPrenom());
                            p_birthdate.setText(data.getDateDeNaissance());
                            p_email.setText(data.getMail());
                            p_phone.setText(data.getPhone());
                            image.setImageBitmap(bitmap);

                        } else {
                            // error
                        }
                    }
                }, profileEmail);

            }

        }
        else {
            final Intent i;
            i = new Intent(ProfilActivity.this, HomeActivity.class);
            startActivity(i);

        }
    }
}

