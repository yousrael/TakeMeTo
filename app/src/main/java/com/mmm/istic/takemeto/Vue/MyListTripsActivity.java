package com.mmm.istic.takemeto.Vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyListTripsActivity extends AppCompatActivity {
    private ListView malistView;
    private SimpleAdapter mlistAdapter;
    // private HashMap<String, String> map2;
    private ArrayList<HashMap<String, String>> mapItems;
    //On déclare la HashMap qui contiendra les informations pour un item
    HashMap<String, String> map;
    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    ArrayList<Trajet> values;
    String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_trips);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        Query query = databaseReference.orderByChild("mail").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Map<String, User> users = new HashMap<String, User>();
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        users.put(userSnapshot.getKey(), user);
                    }

                    ArrayList<User> values = new ArrayList<>(users.values());
                    List<String> keys = new ArrayList<String>(users.keySet());
                    for (User user: values) {
                        name=user.getNom()+""+user.getPrenom();
                    }
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

         TextView welcome=(TextView) findViewById(R.id.textView5);
        welcome.setText(name);

        malistView = ((ListView) findViewById(R.id.listeViewTrajets));
        //Création de la ArrayList qui nous permettra de remplire la listView
        mapItems = new ArrayList<>();
        map = new HashMap<String, String>();
        //Firebase database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("trajets");



       Query querytrajets = databaseReference.orderByChild("user").equalTo(user);


        querytrajets.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Map<String, Trajet> trajets = new HashMap<String, Trajet>();
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        Trajet trajet = userSnapshot.getValue(Trajet.class);
                        trajets.put(userSnapshot.getKey(), trajet);
                    }

                   values = new ArrayList<>(trajets.values());
                }
                else {
                    Log.e("Error","Error in getting trips");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("get List trajets","Failure");
            }
        });
        for (int i = 0; i < values.size(); i++) {

           map=new HashMap<String, String>();
            map.put("departure", values.get(i).getDeparture()+"  ");
            map.put("arrival", values.get(i).getArrival()+"  ");
            map.put("departureDate", values.get(i).getDepartureDate()+"  ");
            map.put("arrivalDate", values.get(i).getArrivalDate());
            mapItems.add(map);
        }

        // Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (mapItems) dans la vue item.xml
        mlistAdapter = new SimpleAdapter(this.getBaseContext(), mapItems, R.layout.item,
                new String[]{"departure", "arrival", "departureDate", "arrivalDate"}, new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4});
        //ici on affecte l'adapteur pour la listView afin de la remplir avec les elemets de item
        malistView.setAdapter(mlistAdapter);

    }
}
