package com.example.boltdogapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boltdogapp.model.Announcement;
import com.example.boltdogapp.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private RelativeLayout rlLogout;

    private String idUser;

    private TextView tvNumeUserConectat;
    private TextView tvEmailUserConectat;



    private ImageView ivProfil,ivReview;

    private FirebaseUser userConectat;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");
    private String numeComplet;
    private EditText petsitterLastname;
    private EditText petsitterFirstname;
    private EditText petsitterPhone;
    private EditText petsitterEmail;
    private User user;


    private Button btn_save;
    private ImageView imageView;
    private ImageView imageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        petsitterLastname=findViewById(R.id.petsitter_lastname);
        petsitterFirstname=findViewById(R.id.petsitter_firstname);
        petsitterPhone=findViewById(R.id.petsitter_phone);
        petsitterEmail=findViewById(R.id.petsitter_email);
        imageView=findViewById(R.id.imgProfile);

        initializeazaAtribute();

        seteazaToolbar();

        seteazaToggle();

        btn_save=findViewById(R.id.save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastname1=petsitterLastname.getText().toString();
                String firstname1=petsitterFirstname.getText().toString();
                String phone1=petsitterPhone.getText().toString();
                String email1=petsitterEmail.getText().toString();






               reference.child("users").child(mAuth.getCurrentUser().getUid()).child("firstname").setValue(firstname1);
               reference.child("users").child(mAuth.getCurrentUser().getUid()).child("lastname").setValue(lastname1);
               reference.child("users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(email1);
               reference.child("users").child(mAuth.getCurrentUser().getUid()).child("phoneNr").setValue(phone1);


                Toast.makeText(EditProfileActivity.this, "Informatii salvate cu succes!", Toast.LENGTH_SHORT).show();




                /*StorageReference fileReference= storageReference.child(name+".png");
                UploadTask uploadTask= fileReference.putFile(imgUri);
                // databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("url").setValue(fileReference.getDownloadUrl());
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
//                            mydb.child("announcements").child(mAuth.getCurrentUser().getUid()).child("url").setValue(downloadUri.toString());
//                            announcement.setPhotoUrl(downloadUri.toString());
                            Announcement announcement=new Announcement(ownername,name,breed,age,description,address);
                            mydb.child("announcements").child(user.getLastname()+" "+user.getFirstname()+" "+name).setValue(announcement);
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });*/


            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        rlLogout.setOnClickListener(this);
        ivProfil.setOnClickListener(this);
        ivReview.setOnClickListener(this);


        incarcaInfoNavMenu();
    }


    private void seteazaToggle() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void seteazaToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void initializeazaAtribute() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigationView);
        tvNumeUserConectat = navigationView.getHeaderView(0).findViewById(R.id.tvNumeUserConectat);
        tvEmailUserConectat = navigationView.getHeaderView(0).findViewById(R.id.tvEmailUserConectat);


        rlLogout = findViewById(R.id.rlLogout);

        userConectat = mAuth.getCurrentUser();
        idUser = userConectat.getUid();


        ivProfil=findViewById(R.id.ivProfile);
        ivReview= findViewById(R.id.ivReview);

    }



    public void incarcaInfoNavMenu() {
        reference.child(idUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if (user != null) {
                            String nume = user.getLastname();
                            String prenume = user.getFirstname();
                            numeComplet = nume + " " + prenume;
                            String email =user.getEmail();


                            tvNumeUserConectat.setText(numeComplet);

                            tvEmailUserConectat.setText(email);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("preluareOwner", error.getMessage());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivProfile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                //finish();
                break;
            case R.id.ivReview:
                // startActivity(new Intent(getApplicationContext(), ReviewActivity.class));
                break;

            case R.id.rlLogout:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Sunteti sigur ca vreti sa va deconectati?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(EditProfileActivity.this, LoginActivity.class));

                        finish();
                    }
                });
                builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_view_announcements:
                startActivity(new Intent(getApplicationContext(),ViewAnnouncementActivity.class));
                finish();
                break;
            case R.id.nav_status_request:
                startActivity(new Intent(getApplicationContext(),StatusRequestActivity.class));
                finish();
                break;

        }
        return true;
    }

}