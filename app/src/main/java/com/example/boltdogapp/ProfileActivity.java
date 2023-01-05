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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boltdogapp.model.Announcement;
import com.example.boltdogapp.model.Request;
import com.example.boltdogapp.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private RelativeLayout rlLogout;

    private String idUser;

    private TextView tvNumeUserConectat;
    private TextView tvEmailUserConectat;


    private ImageView imageView;
    private ImageView  ivReview;

    private FirebaseUser userConectat;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");
    private String numeComplet;

    User user;
    TextView NumePetsitter,PrenumePetsitter,NrTelPetsitter,EmailPetsitter;
    Button btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();

        NumePetsitter=findViewById(R.id.numePetsitter);
        PrenumePetsitter=findViewById(R.id.prenumePetsitter);
        NrTelPetsitter=findViewById(R.id.telefonPetsitter);
        EmailPetsitter=findViewById(R.id.emailPetsitter);
        btnEdit=findViewById(R.id.btn_edit);
        initializeazaAtribute();

        seteazaToolbar();

        seteazaToggle();


        navigationView.setNavigationItemSelectedListener(this);
        rlLogout.setOnClickListener(this);
        ivReview.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        reference.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        incarcaInfoNavMenu();
        incarcaInfoProfile();


    }


    private void seteazaToggle() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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


        ivReview = findViewById(R.id.ivReview);

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
                            String email = user.getEmail();


                            tvNumeUserConectat.setText(numeComplet);

                            tvEmailUserConectat.setText(email);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("preluarePetsitter", error.getMessage());
                    }
                });
    }
    public void incarcaInfoProfile() {
        reference.child(idUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if (user != null) {
                            String nume = user.getLastname();
                            String prenume = user.getFirstname();
                            String telefon=user.getPhoneNr();
                            String email = user.getEmail();



                            NumePetsitter.setText(nume);
                            PrenumePetsitter.setText(prenume);
                            NrTelPetsitter.setText(telefon);
                            tvEmailUserConectat.setText(email);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("preluarePetsitter", error.getMessage());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                //startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
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
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

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

        }
        return true;
    }




}
