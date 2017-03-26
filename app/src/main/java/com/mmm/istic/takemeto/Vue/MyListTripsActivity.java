package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.mmm.istic.takemeto.dao.SimpleCallback;
import com.mmm.istic.takemeto.dao.UserDao;
import com.mmm.istic.takemeto.dao.UserDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyListTripsActivity extends AppCompatActivity {
    private ListView malistView;
    private ListView malistViewReservations;
    private SimpleAdapter mlistAdapter;
    // private HashMap<String, String> map2;
    private ArrayList<HashMap<String, String>> mapItems;
    private ArrayList<HashMap<String, String>> mapItemsReservation;
    //On déclare la HashMap qui contiendra les informations pour un item
    HashMap<String, String> map;
    HashMap<String, String> mapReservation;
    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    ArrayList<Trajet> values;
    UserDao userdao = new UserDaoImpl();
    User userbase;
    String email;
    String keyUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_trips);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
        userdao.findUserbyEmail(new SimpleCallback<User>() {
            @Override
            public void callback(User data) {
                if (data != null) {
                    userbase = data;
                    TextView welcome = (TextView) findViewById(R.id.textView5);
                    welcome.setText("Welcome  " + userbase.getNom() + "  " + userbase.getPrenom());
                } else {
                    Log.e("Error", "Error in getting user");
                }
            }
        }, email);


        malistView = ((ListView) findViewById(R.id.listeViewTrajets));
        malistViewReservations = ((ListView) findViewById(R.id.listeViewReservations));
        //Création de la ArrayList qui nous permettra de remplire la listView
        mapItems = new ArrayList<>();
        mapItemsReservation = new ArrayList<>();
        map = new HashMap<String, String>();
        mapReservation = new HashMap<String, String>();
        //Firebase database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("trajets");
        userdao.findUserKeybyEmail(new SimpleCallback<String>() {
            @Override
            public void callback(String data) {
                if (data != null) {
                    keyUser = data;
                    Query querytrajets = databaseReference.orderByChild("user").equalTo(keyUser);


                    querytrajets.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                Map<String, Trajet> trajets = new HashMap<String, Trajet>();

                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                                    Trajet trajet = userSnapshot.getValue(Trajet.class);
                                    trajets.put(userSnapshot.getKey(), trajet);

                                }


                                values = new ArrayList<>(trajets.values());
                                for (int i = 0; i < values.size(); i++) {

                                    map = new HashMap<String, String>();
                                    map.put("departure", values.get(i).getDeparture() + "  ");
                                    map.put("arrival", values.get(i).getArrival() + "  ");
                                    map.put("departureDate", values.get(i).getDepartureDate() + "  ");
                                    map.put("arrivalDate", values.get(i).getArrivalDate());
                                }

                                // Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (mapItems) dans la vue item.xml
                                mlistAdapter = new SimpleAdapter(getBaseContext(), mapItems, R.layout.item,
                                        new String[]{"departure", "arrival", "departureDate", "arrivalDate", "places", "prixTrajet"}, new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6});
                                //ici on affecte l'adapteur pour la listView afin de la remplir avec les elemets de item
                                malistView.setAdapter(mlistAdapter);

                            } else {
                                Log.e("Error", "Error in getting trips");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("get List trajets", "Failure");
                        }
                    });


                } else {
                    Log.e("Error", "Error in getting user");
                }
            }
        }, email);

        userdao.findUserKeybyEmail(new SimpleCallback<String>() {
            @Override
            public void callback(String data) {
                if (data != null) {
                    keyUser = data;

                    Query querytrajetsVoyageurs = databaseReference.orderByChild("vayageurs");


                    querytrajetsVoyageurs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                Map<String, Trajet> trajets = new HashMap<String, Trajet>();
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    Trajet trajet = userSnapshot.getValue(Trajet.class);
                                    trajets.put(userSnapshot.getKey(), trajet);
                                }


                                values = new ArrayList<>(trajets.values());
                                for (Trajet trajet : values) {

                                    for (Map.Entry voyageur : trajet.getVayageurs().entrySet()) {
                                        if (voyageur.getValue().equals(keyUser)) {
                                            mapReservation = new HashMap<String, String>();
                                            mapReservation.put("departure", trajet.getDeparture() + "  ");
                                            mapReservation.put("arrival", trajet.getArrival() + "  ");
                                            mapReservation.put("departureDate", trajet.getDepartureDate() + "  ");
                                            mapReservation.put("arrivalDate", trajet.getArrivalDate());
                                            mapReservation.put("user", trajet.getUser());
                                            mapItemsReservation.add(mapReservation);
                                        }
                                    }
                                }
//                                for (int i = 0; i < values.size(); i++) {
//                                    for (int j = 0; j < values.get(i).getVayageurs().size(); j++) {
//                                        if (values.get(i).getVayageurs().get(j) == keyUser) {
//                                            mapReservation = new HashMap<String, String>();
//                                            mapReservation.put("departure", values.get(i).getDeparture() + "  ");
//                                            mapReservation.put("arrival", values.get(i).getArrival() + "  ");
//                                            mapReservation.put("departureDate", values.get(i).getDepartureDate() + "  ");
//                                            mapReservation.put("arrivalDate", values.get(i).getArrivalDate());
//                                            mapReservationItemsReservation.add(mapReservation);
//                                        }
//                                    }
//                                }

                                // Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (mapItems) dans la vue item.xml
                                mlistAdapter = new SimpleAdapter(getBaseContext(), mapItemsReservation, R.layout.item,
                                        new String[]{"departure", "arrival", "departureDate", "arrivalDate", "places", "prixTrajet","user"}, new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6,R.id.textView17});
                                //ici on affecte l'adapteur pour la listView afin de la remplir avec les elemets de item
                                malistViewReservations.setAdapter(mlistAdapter);

                            } else {
                                Log.e("Error", "Error in getting trips");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("get List trajets", "Failure");
                        }
                    });


                } else {
                    Log.e("Error", "Error in getting user");
                }
            }
        }, email);
        malistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MyListTripsActivity.this, TripActivity.class);
                String departure = ((TextView) view.findViewById(R.id.textView1)).getText().toString();
                String arrival = ((TextView) view.findViewById(R.id.textView2)).getText().toString();
                String departureDate = ((TextView) view.findViewById(R.id.textView3)).getText().toString().split("_")[0];
                String arrivalDate = ((TextView) view.findViewById(R.id.textView4)).getText().toString().split("_")[0];
                String places = ((TextView) view.findViewById(R.id.textView5)).getText().toString();
                String prixTrajet = ((TextView) view.findViewById(R.id.textView6)).getText().toString();

                intent.putExtra("departure", departure);
                intent.putExtra("arrival", arrival);
                intent.putExtra("departureDate", departureDate);
                intent.putExtra("arrivalDate", arrivalDate);
                intent.putExtra("places", places);
                intent.putExtra("prixTrajet", prixTrajet);
                intent.putExtra("emailUser", email);
                startActivity(intent);
            }
        });

        malistViewReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MyListTripsActivity.this, TripActivity.class);
                String departure = ((TextView) view.findViewById(R.id.textView1)).getText().toString();
                String arrival = ((TextView) view.findViewById(R.id.textView2)).getText().toString();
                String departureDate = ((TextView) view.findViewById(R.id.textView3)).getText().toString();
                String arrivalDate = ((TextView) view.findViewById(R.id.textView4)).getText().toString();
                String places = ((TextView) view.findViewById(R.id.textView5)).getText().toString();
                String prixTrajet = ((TextView) view.findViewById(R.id.textView6)).getText().toString();
                String keyUser = ((TextView) view.findViewById(R.id.textView17)).getText().toString();
                intent.putExtra("departure", departure);
                intent.putExtra("arrival", arrival);
                intent.putExtra("departureDate", departureDate);
                intent.putExtra("arrivalDate", arrivalDate);
                intent.putExtra("places", places);
                intent.putExtra("prixTrajet", prixTrajet);
                intent.putExtra("keyUser", keyUser);
                startActivity(intent);
            }
        });


    }

}