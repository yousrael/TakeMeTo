package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String email;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button button = (Button)findViewById(R.id.button1);
        button.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();

//        int requestCode = intent.getIntExtra("requestCode",1);
//        if(requestCode == 0){


        UserDaoImpl serviceUser = new UserDaoImpl();

        serviceUser.findUserbyEmail(new SimpleCallback<User>() {
            @Override
            public void callback(User data) {
                if (data != null) {
                    Log.e("QSDQSDQDS","User.email: "+data.getMail());
                } else {
                    // error
                }
            }
        },"user1@takemeto.com");

//
//                //Own user account
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            if (user != null) {
//                // User is signed in
//                email = user.getEmail();
//
//                Log.d("", "onAuthStateChanged:signed_in:" + user.getUid());
//            } else {
//                // User is signed out
//                Log.d("", "onAuthStateChanged:signed_out");
//            }
//        Log.e("get User by email", "email: "+email);
//        databaseReference = FirebaseDatabase.getInstance().getReference("users");
//        Query query = databaseReference.orderByChild("mail").equalTo(email);
//        //Id d'un objet de la BD pour test : "-KfobKb7oMRm1JMQvl8L", a pares "users" ci-dessus précèder d'un "/"
//        //databaseReference = FirebaseDatabase.getInstance().getReference("users/"+id);
//        Log.e("db ref",databaseReference.toString());
//
//       // query= databaseReference.getRef();
//        //Log.d("query", query.getRef().toString());
//        //query= databaseReference.getRef();
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                   /// for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                    if(dataSnapshot.getValue() != null){
//                        Map<String, User> users = new HashMap<String, User>();
//                        for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
//                            User user = jobSnapshot.getValue(User.class);
//                            users.put(jobSnapshot.getKey(), user);
//                        }
//
//                        ArrayList<User> values = new ArrayList<>(users.values());
//                        List<String> keys = new ArrayList<String>(users.keySet());
//                        for (User user: values) {
//                            Log.d("firebase", user.getPrenom());
//                        }
//                        /*Log.d("class name of user ", dataSnapshot.getValue().getClass().getName());
//                        Log.d("real user ", dataSnapshot.getValue(User.class).getClass().getName());
//                        Log.e("get User by email","SUCCESS"+dataSnapshot.getValue(User.class));*/
//                        //Log.e("get User by email","SUCCESS"+user.getNom());
//                    }
//                    else {
//                        Log.e("null user","null user");
//                    }
//
//
//                   // }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Log.d("get User by email","Failure");
//                }
//            });

        /*else if(requestCode == 1){
            //Other user account
            button.setText("Send message");
            button.setEnabled(false);
            //TODO:Envoyer un message à un autre utilisateur
        }*/
    //}
}
}
