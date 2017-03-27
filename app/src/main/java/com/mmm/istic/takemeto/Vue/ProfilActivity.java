package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class ProfilActivity extends AppCompatActivity{


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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i;
                i = new Intent(ProfilActivity.this, FormModificationActivity.class);
                startActivity(i);
            }
        });
        }

        else if(requestCode == 1){
            Log.e("Profile","other user");
            //Other user account
            button.setText("Send message");
            button.setEnabled(false);
            //TODO:Envoyer un message à un autre utilisateur


            String profileEmail = intent.getStringExtra("email");
            Log.e("Profile","email: "+profileEmail);
            if(profileEmail != null) {
                UserDaoImpl serviceUser = new UserDaoImpl();

                Log.e("Profile","searching for user: "+profileEmail);
                serviceUser.findUserbyEmail(new SimpleCallback<User>() {
                    @Override
                    public void callback(User data) {
                        Log.e("Profile callback","Callback with user "+data);
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
                            Log.e("Profile callback","Callback error");
                        }
                    }
                }, profileEmail);

            }

        }
        else {

            Log.e("Profile","going home");
            final Intent i;
            i = new Intent(ProfilActivity.this, HomeActivity.class);
            startActivity(i);

        }
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

