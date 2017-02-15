package com.mmm.istic.takemeto.Vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mmm.istic.takemeto.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            case R.id.seetings:
                seetings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void seetings() {
        //TODO
    }

    private void affichProfil() {
        //TODO
    }

    private void getTrips() {
        //TODO

    }

}
