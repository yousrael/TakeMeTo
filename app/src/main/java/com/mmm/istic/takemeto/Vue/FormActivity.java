package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mmm.istic.takemeto.R;

public class FormActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText password;

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

        email = (EditText) findViewById(R.id.edit_newuser_email);
        password = (EditText) findViewById(R.id.edit_newuser_password);

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
    }

    private void createNewUser() {
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("add user", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            Intent i=new Intent(FormActivity.this, HomeActivity.class);
                            startActivity(i);
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error adding new user",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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
