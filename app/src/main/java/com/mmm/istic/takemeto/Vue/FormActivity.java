package com.mmm.istic.takemeto.Vue;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText email;
    private EditText password;
    private EditText nom;
    private EditText prenom;
    private EditText dateDeNaissance;
    private EditText phone;

    //For the DatePicker dialog
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //intent.putExtra("requestCode", requestCode)
        //startActivityForResult(intent, NEW_CLIENT);
        Button createuser = (Button) findViewById(R.id.CreateNewUser);
        TextView textview1 = (TextView) findViewById(R.id.textView6);
        TextView textview2 = (TextView) findViewById(R.id.textView7);

        email = (EditText) findViewById(R.id.edit_form_email);
        password = (EditText) findViewById(R.id.edit_form_password);
        nom = (EditText) findViewById(R.id.edit_form_nom);
        prenom = (EditText) findViewById(R.id.edit_form_prenom);
        dateDeNaissance = (EditText) findViewById(R.id.edit_form_date_naissance);
        //Dialog to select a date, see also updateDateDeNaissance() at the bottom
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateDeNaissance();
            }

        };
        dateDeNaissance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FormActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //end date dialog
        phone = (EditText) findViewById(R.id.edit_form_phone);

        createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();

            }
        });

/*        if(intent.getIntExtra("requestCode",0) == 0){
            //New user creation
            button.setText("Create new user");
            textview1.setText("Register Form");
            textview1.setText(" please answer all the input");
        }
        else if(intent.getIntExtra("requestCode",1) == 1){
            //Modify existing user
            button.setText("Apply modification");
            textview1.setText("Modification Form");
            textview1.setText(" please answer all the input");
        }
    }*/
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

    }

    //Updating the after selectining it from the dialog
    private void updateDateDeNaissance() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateDeNaissance.setText(sdf.format(myCalendar.getTime()));
    }

    //Create a new user and store it in firebase database
    private void createNewUser() {
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("add user", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            addNewUser();
                            Intent i=new Intent(FormActivity.this, HomeActivity.class);
                            startActivity(i);
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error adding new user",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void addNewUser() {
        String key =  databaseReference.push().getKey(); //generating a key
       databaseReference.child(key).setValue(new User(nom.getText().toString(),
                prenom.getText().toString(),
                email.getText().toString(),
                phone.getText().toString(),
                dateDeNaissance.getText().toString()));
    }

   /* protected  void onClickFunction(){
      //  if(intent.getIntExtra("requestCode",0) == 0){
            //New user creation
            Intent i=new Intent(FormActivity.this,HomeActivity.class);
            startActivity(i);
      /*  }
        else if(intent.getIntExtra("requestCode",1) == 1){
            //Modify existing user

        }*/

}
