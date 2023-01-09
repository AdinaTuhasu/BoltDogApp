package com.example.boltdogapp.owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boltdogapp.R;
import com.example.boltdogapp.authentification.LoginActivity;
import com.example.boltdogapp.model.Announcement;
import com.example.boltdogapp.model.Request;
import com.example.boltdogapp.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addAnnouncementActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_CODE =1234 ;
    private static final int CAPTURE_CODE =1001 ;
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
    private EditText petName;
    private EditText petBreed;
    private EditText petAge;
    private EditText petDescription;
    private EditText petAddress;
    private User user;


    private Button addButton;
    private Button openCamera;
    private Uri imgUri;

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        petName=findViewById(R.id.pet_name);
        petAddress=findViewById(R.id.pet_address);
        petBreed=findViewById(R.id.pet_breed);
        petAge=findViewById(R.id.pet_age);
        petDescription=findViewById(R.id.pet_description);
        imageView=findViewById(R.id.pet_camera_image);
        imgUri=Uri.parse(imageView.toString());
        openCamera=findViewById(R.id.open_camera);
        initializeazaAtribute();

        seteazaToolbar();

        seteazaToggle();


        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED ){
                        String[] permision={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permision,PERMISSION_CODE);
                    }
                    else{
                        openCamera1();

                    }
                }
                else{
                    openCamera1();
                }
            }
        });


        
        
        
        addButton=findViewById(R.id.add_announce);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=petName.getText().toString();
                int age=Integer.parseInt(petAge.getText().toString());
                String breed=petBreed.getText().toString();
                String description=petDescription.getText().toString();
                String address=petAddress.getText().toString();
                String ownername=user.getLastname()+" "+user.getFirstname();
                Announcement announcement=new Announcement(ownername,name,breed,age,description,address);

                reference.child("announcements").child(user.getLastname()+" "+user.getFirstname()+" "+name).setValue(announcement);
                Toast.makeText(addAnnouncementActivity.this, "Anunt adaugat cu succes!", Toast.LENGTH_SHORT).show();




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


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            imageView.setImageURI(imgUri);


        }
    }

    private void openCamera1() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Imagine noua");
        values.put(MediaStore.Images.Media.DESCRIPTION,"De la Camera");
        imgUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        startActivityForResult(intent,CAPTURE_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera1();
                }
                else{
                    Toast.makeText(this, "Permisiunea a fost anulata!", Toast.LENGTH_SHORT).show();
                }
        }
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

        ivProfil = findViewById(R.id.ivProfile);
        ivReview= findViewById(R.id.ivReview);

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
                startActivity(new Intent(getApplicationContext(), OwnerProfileActivity.class));
                finish();
                break;
            case R.id.ivReview:
                startActivity(new Intent(getApplicationContext(), OwnerReviewActivity.class));
                finish();
                break;

            case R.id.rlLogout:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Sunteti sigur ca vreti sa va deconectati?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(addAnnouncementActivity.this, LoginActivity.class));

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
            case R.id.nav_add_announcements:
                startActivity(new Intent(getApplicationContext(),addAnnouncementActivity.class));
                break;
            case R.id.nav_view_requests:
                startActivity(new Intent(getApplicationContext(), ViewRequests.class));
                break;
        }
        return true;
    }
}