package com.example.boltdogapp.petsitter;

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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.boltdogapp.R;
import com.example.boltdogapp.authentification.LoginActivity;
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
import com.squareup.picasso.Picasso;

public class ProfileForOwner extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private RelativeLayout rlLogout;

    private String idUser;

    private TextView tvNumeUserConectat;
    private TextView tvEmailUserConectat;
    private ImageView image_profile;

    private ImageView imageView;
    private ImageView ivProfil, ivReview;

    private FirebaseUser userConectat;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");
    private String numeComplet;
    private User owner;
    TextView lastname,firstname,email,phone;
    ImageView image_o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_owner);
        Intent i=getIntent();
        owner=(User) i.getSerializableExtra("owner_profile");
        image_o=findViewById(R.id.image_o);
        lastname=findViewById(R.id.lastname_o);
        firstname=findViewById(R.id.firstname_o);
        email=findViewById(R.id.email_o);
        phone=findViewById(R.id.phone_o);
        lastname.setText(owner.getLastname());
        firstname.setText(owner.getFirstname());
        email.setText(owner.getEmail());
        phone.setText(owner.getPhoneNr());
        String img= owner.getPhotoUrl();
        Picasso.get().load(img).into(image_o);
        initializeazaAtribute();

        seteazaToolbar();

        seteazaToggle();



        navigationView.setNavigationItemSelectedListener(this);
        rlLogout.setOnClickListener(this);
        ivProfil.setOnClickListener(this);
        ivReview.setOnClickListener(this);


        incarcaInfoNavMenu();
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

        image_profile=navigationView.getHeaderView(0).findViewById(R.id.imgProfile);
        rlLogout = findViewById(R.id.rlLogout);

        userConectat = mAuth.getCurrentUser();
        idUser = userConectat.getUid();

        ivProfil = findViewById(R.id.ivProfile);
        ivReview = findViewById(R.id.ivReview);

    }


    public void incarcaInfoNavMenu() {
        reference.child("users").child(mAuth.getCurrentUser().getUid())
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
                            String img= user.getPhotoUrl().toString();
                            Picasso.get().load(img).into(image_profile);

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

            case R.id.ivProfile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
                break;
            case R.id.ivReview:
                startActivity(new Intent(getApplicationContext(), PetsitterReviewActivity.class));
                break;

            case R.id.rlLogout:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Sunteti sigur ca vreti sa va deconectati?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(ProfileForOwner.this, LoginActivity.class));

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
                startActivity(new Intent(getApplicationContext(), ViewAnnouncementActivity.class));
                finish();
                break;
            case R.id.nav_status_request:
                startActivity(new Intent(getApplicationContext(), StatusRequestActivity.class));
                finish();
                break;

        }
        return true;
    }
}