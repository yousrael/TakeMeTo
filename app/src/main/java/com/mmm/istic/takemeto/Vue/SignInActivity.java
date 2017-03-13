package com.mmm.istic.takemeto.Vue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mmm.istic.takemeto.R;

public class SignInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signin = (Button) findViewById(R.id.button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        TextView signup = (TextView) findViewById(R.id.textView4);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, FormActivity.class);
                startActivity(i);

            }

        });

        email = (EditText) findViewById(R.id.edit_signin_mail);
        password = (EditText) findViewById(R.id.edit_signin_password);

        firebaseAuth = FirebaseAuth.getInstance();


    }

    public void signIn() {

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("sign in", "signInWithEmail:onComplete:" + task.isSuccessful());
                            Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(i);
                        }

                        if (!task.isSuccessful()) {
                            Log.d("sign in", "sign in failed ");
                        }
                    }
                });
    }
}
