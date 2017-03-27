package com.mmm.istic.takemeto.Vue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mmm.istic.takemeto.R;
import com.mmm.istic.takemeto.dao.SimpleCallback;
import com.mmm.istic.takemeto.dao.UserDaoImpl;
import com.mmm.istic.takemeto.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FormModificationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String globalData;
    private EditText email;
    private EditText password;
    private EditText nom;
    private EditText prenom;
    private EditText dateDeNaissance;
    private EditText phone;
    private Button createuser;
    String  userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageView image;

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
        image= (ImageView) findViewById(R.id.imageView2);
        createuser = (Button) findViewById(R.id.CreateNewUser);
        TextView textview1 = (TextView) findViewById(R.id.textView6);
        TextView textview2 = (TextView) findViewById(R.id.textView7);

        email = (EditText) findViewById(R.id.edit_suggest_trip_date_arrivee);
        password = (EditText) findViewById(R.id.edit_suggest_trip_arrival_place);
        nom = (EditText) findViewById(R.id.edit_form_nom);
        prenom = (EditText) findViewById(R.id.edit_form_prenom);
        dateDeNaissance = (EditText) findViewById(R.id.edit_suggest_trip_depart_place);
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
                new DatePickerDialog(FormModificationActivity.this, date, myCalendar
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



        //user deja logger
        final UserDaoImpl serviceUser = new UserDaoImpl();
        if(serviceUser.GetUser() != null){
            TextView t_title = (TextView) findViewById(R.id.f_title);
            t_title.setText("Modification Form");
            createuser.setText("APPLY MODIFICATION");
            serviceUser.findUserbyEmail(new SimpleCallback<User>() {
                @Override
                public void callback(User data) {

                 email.setText(data.getMail());
                    email.setEnabled(false);
                    nom.setText(data.getNom());
                    prenom.setText(data.getPrenom());
                    phone.setText(data.getPhone());
                    dateDeNaissance.setText(data.getDateDeNaissance());
                    password.setEnabled(false);
                    byte [] encodeByte=Base64.decode(data.getImage(),Base64.DEFAULT);
                    Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    image.setImageBitmap(bitmap);

                    serviceUser.findUserKeybyEmail(new SimpleCallback<String>() {
                        @Override
                        public void callback(String data) {
                            globalData = data;
                            createuser.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bitmap largeIcon=  ((BitmapDrawable) image.getDrawable()).getBitmap();
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    largeIcon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                    byte[] byteFormat = stream.toByteArray();
                                    String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

                                    databaseReference = FirebaseDatabase.getInstance().getReference("users");
                                    databaseReference.child(globalData).setValue(
                                            new User(
                                                    nom.getText().toString(),
                                                    prenom.getText().toString(),
                                                    email.getText().toString(),
                                                    phone.getText().toString(),
                                                    dateDeNaissance.getText().toString(), encodedImage));
                                    Toast.makeText(FormModificationActivity.this, "Modification done", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(FormModificationActivity.this, HomeActivity.class);
                                    startActivity(i);

                                }
                            });

                        }
                    },serviceUser.GetUser());
                }
            },serviceUser.GetUser());
        }
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
                            Intent i = new Intent(FormModificationActivity.this, HomeActivity.class);
                            startActivity(i);
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error adding new user", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void addNewUser() {
        String key = databaseReference.push().getKey(); //generating a key
       // Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.id.imageView2);
        Bitmap largeIcon=  ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
       largeIcon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        databaseReference.child((String.valueOf(key))).setValue(new User(nom.getText().toString(),
                prenom.getText().toString(),
                email.getText().toString(),
                phone.getText().toString(),
                dateDeNaissance.getText().toString(),encodedImage));
    }

    //Choose profil image
    public void chooseProfilImage(View view) {

        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(FormModificationActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(FormModificationActivity.this);
                if (items[item].equals("Take Photo")) {
                   userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
       // thumbnail.recycle();
      //  byte[] byteArray = bytes.toByteArray();
       // String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       image.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       image.setImageBitmap(bm);
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
        i = new Intent(FormModificationActivity.this, HomeActivity.class);
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
