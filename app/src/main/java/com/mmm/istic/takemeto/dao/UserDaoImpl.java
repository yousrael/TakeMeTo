package com.mmm.istic.takemeto.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A data Access Object for User
 */
public class UserDaoImpl implements UserDao {


    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("trajets");

    private FirebaseAuth firebaseAuth;
    private ArrayList<User> foundUsers;
    @Override
//    public User findUserbyEmail(String email) {
    public void findUserbyEmail(@NonNull final SimpleCallback<User> finishedCallback, String email){
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseReference.orderByChild("mail").equalTo(email);
        //Id d'un objet de la BD pour test : "-KfobKb7oMRm1JMQvl8L", a pares "users" ci-dessus précèder d'un "/"
        //databaseReference = FirebaseDatabase.getInstance().getReference("users/"+id);
        Log.e("db ref",databaseReference.toString());

        // query= databaseReference.getRef();
        //Log.d("query", query.getRef().toString());
        //query= databaseReference.getRef();
        ArrayList<User> user = null;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /// for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                if(dataSnapshot.getValue() != null){
                    Map<String, User> users = new HashMap<String, User>();
                    for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                        User user = jobSnapshot.getValue(User.class);
                        users.put(jobSnapshot.getKey(), user);
                    }

                    foundUsers = new ArrayList<>(users.values());
                    List<String> keys = new ArrayList<String>(users.keySet());
                    for (User user: foundUsers) {
                        Log.d("firebase user find :",user.getMail());
                    }
                    if(foundUsers.size() ==1)
                        finishedCallback.callback(foundUsers.get(0));
                    else
                        finishedCallback.callback(null);
                        /*Log.d("class name of user ", dataSnapshot.getValue().getClass().getName());
                        Log.d("real user ", dataSnapshot.getValue(User.class).getClass().getName());
                        Log.e("get User by email","SUCCESS"+dataSnapshot.getValue(User.class));*/
                    //Log.e("get User by email","SUCCESS"+user.getNom());
                }
                else {
                    Log.e("null user","null user");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("get User by email","Failure");
            }
        });
    }

    @Override
//    public String findUserKeybyEmail(String email) {
    public void findUserKeybyEmail(@NonNull final SimpleCallback<String> finishedCallback, String email){
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseReference.orderByChild("mail").equalTo(email);
        //Id d'un objet de la BD pour test : "-KfobKb7oMRm1JMQvl8L", a pares "users" ci-dessus précèder d'un "/"
        //databaseReference = FirebaseDatabase.getInstance().getReference("users/"+id);
        Log.e("db ref",databaseReference.toString());

        // query= databaseReference.getRef();
        //Log.d("query", query.getRef().toString());
        //query= databaseReference.getRef();
        ArrayList<User> user = null;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /// for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                if(dataSnapshot.getValue() != null){
                    Map<String, User> users = new HashMap<String, User>();
                    for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                        User user = jobSnapshot.getValue(User.class);
                        users.put(jobSnapshot.getKey(), user);
                    }

                    foundUsers = new ArrayList<>(users.values());
                    List<String> keys = new ArrayList<String>(users.keySet());
                    for (User user: foundUsers) {
                        Log.d("firebase user find :",user.getMail());
                    }
                    if(foundUsers.size() ==1)
                        finishedCallback.callback(keys.get(0));
                    else
                        finishedCallback.callback(null);
                        /*Log.d("class name of user ", dataSnapshot.getValue().getClass().getName());
                        Log.d("real user ", dataSnapshot.getValue(User.class).getClass().getName());
                        Log.e("get User by email","SUCCESS"+dataSnapshot.getValue(User.class));*/
                    //Log.e("get User by email","SUCCESS"+user.getNom());
                }
                else {
                    Log.e("null user","null user");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("get User by email","Failure");
            }
        });

    }

    @Override
    public String GetUser() {
        String email=null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            email = user.getEmail();

            Log.d("UserDao", "signed in as"+email+"   " + user.getUid());

        } else {
            // User is signed out
            Log.d("UserDao", "signed out");
        }
        return email;
    }
}


/*
Exemple appel

UserDaoImpl serviceUser = new UserDaoImpl();

String email = serviceUser.GetUser();

serviceUser.findUserbyEmail(new SimpleCallback<User>() {
    @Override
    public void callback(User data) {
        if (data != null) {
            Log.e("QSDQSDQDS","User.email: "+data.getMail());
        } else {
            // error
        }
    }
},email);

 */
