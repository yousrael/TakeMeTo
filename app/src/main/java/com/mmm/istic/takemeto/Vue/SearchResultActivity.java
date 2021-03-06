package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.TrajetDao;
import com.mmm.istic.takemeto.dao.TrajetDaoImpl;
import com.mmm.istic.takemeto.model.Trajet;
import com.mmm.istic.takemeto.model.User;
import com.mmm.istic.takemeto.util.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    //To get the criteria filter via intent from the previous activity
    private String departureDate;
    private String arrivalDate;
    private String departure;
    private String arrival;

    //Firebase database
    private DatabaseReference databaseReference;

    //DAO filter
    TrajetDao trajetDao;
    Criteria criteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        final Intent intent = getIntent();
        departureDate = intent.getStringExtra("departureDate");
        arrivalDate = intent.getStringExtra("arrivalDate");
        departure = intent.getStringExtra("departure");
        arrival = intent.getStringExtra("arrival");

        /*Log.d("depart date", departureDate);
        Log.d("arrival date", arrivalDate);
        Log.d("depart place", departure);
        Log.d("arrival place", arrival);*/

        /*trajetDao = new TrajetDaoImpl();
        criteria = new Criteria(departureDate+"_"+departure+"_"+arrival, departure, arrival);

        List<Trajet> trajets = trajetDao.findTrajetbyCriteria(criteria);*/
        /*Log.d("La liste des trajets", trajets.toString());
        Log.d("taille de la liste", String.valueOf(trajetDao.findTrajetbyCriteria(criteria).size()));*/


        databaseReference = FirebaseDatabase.getInstance().getReference("trajets");
        Query query = databaseReference.orderByChild("departureDate").equalTo(departureDate+"_"+departure+"_"+arrival);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Map<String, Trajet> trajets = new HashMap<String, Trajet>();
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        Trajet trajet = userSnapshot.getValue(Trajet.class);
                        Log.d("current trajet key :", userSnapshot.getKey());
                        trajets.put(userSnapshot.getKey(), trajet);
                    }

                    final ArrayList<Trajet> values = new ArrayList<>(trajets.values());



                    int length = values.size();
                    String[] trajetStrings = new String[length];
                    for (int i = 0; i < length; i++) {
                        trajetStrings[i] = values.get(i).getDeparture()+"\n"+values.get(i).getArrival()+"\n"+values.get(i).getDepartureDate().split("_")[0];
                    }

                    final List<String> keys = new ArrayList<String>(trajets.keySet());

                    for (Trajet trajet : values) {
                        Log.d("firebase arrival date ", trajet.getArrivalDate());
                        Log.d("firebase available sits", String.valueOf(trajet.getPlaces()));
                        Log.d("firebase trajet price ", String.valueOf(trajet.getPrixTrajet()));
                    }

                    ListView mListView = (ListView) findViewById(R.id.listView_searchTrip);

                    //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
                    //Contenant une TextView avec comme identifiant "@android:id/text1"

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchResultActivity.this,
                            android.R.layout.simple_list_item_1, trajetStrings);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent1=new Intent(SearchResultActivity.this, TripActivity.class);

                            intent1.putExtra("key",keys.get(position));
                            Log.d("key in Search",keys.get(position));
                            intent1.putExtra("user",values.get(position).getUser());
                            intent1.putExtra("departure",values.get(position).getDeparture());
                            intent1.putExtra("arrival",values.get(position).getArrival());
                            intent1.putExtra("departureDate",values.get(position).getDepartureDate());
                            intent1.putExtra("arrivalDate",values.get(position).getArrivalDate());
                            intent1.putExtra("prixTrajet",values.get(position).getPrixTrajet());
                            intent1.putExtra("places",values.get(position).getPlaces());
                            intent1.putExtra("keyUser",values.get(position).getUser());
                            Log.e("Item Clicked",keys.get(position));
                            startActivity(intent1);


                        }
                    });

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
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                Toast.makeText(this, "You're disconnected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.home:
                goBackHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBackHome() {
        //  FirebaseAuth.AuthStateListener mAuthListener;
        final Intent i;
        i = new Intent(SearchResultActivity.this, HomeActivity.class);
        startActivity(i);
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
