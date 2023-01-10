package com.example.boltdogapp.authentification;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.boltdogapp.R;
import com.example.boltdogapp.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity {
    private EditText Lastname, Firstname, Email, PhoneNr, Username, Password;
    private Button signup_button, radio_button_petsitter, radio_button_owner;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference("userProfile");
    ImageView profileImage;
    Button choose_photo;
    private DatabaseReference mydb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private boolean petsitter = false;
    Uri imgUri;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firstname = findViewById(R.id.firstname);
        Lastname = findViewById(R.id.lastname);
        Email = findViewById(R.id.email);
        PhoneNr = findViewById(R.id.phoneNr);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        signup_button = findViewById(R.id.register_button);
        radio_button_owner = findViewById(R.id.owner);
        radio_button_petsitter = findViewById(R.id.petsitter);
        profileImage=findViewById(R.id.profile_image);
        choose_photo=findViewById(R.id.choose_photo);
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery=new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mGetContent.launch("image/*");
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lastname1 = Lastname.getText().toString();
                String firstname1 = Firstname.getText().toString();
                String email1 = Email.getText().toString();
                String phone1 = PhoneNr.getText().toString();
                String username1 = Username.getText().toString();
                String password1 = Password.getText().toString();

                if (lastname1.isEmpty() || firstname1.isEmpty() || email1.isEmpty() || phone1.isEmpty() || username1.isEmpty() || password1.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Va rugam completati toate campurile", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                StorageReference fileReference= storageReference.child(username1+"."+getFileExtension(imgUri));

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
                                             user = new User(lastname1, firstname1, email1, phone1, username1, password1, petsitter,downloadUri.toString());
                                            mydb.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);


                                        } else {
                                            // Handle failures
                                            // ...
                                        }
                                    }
                                });
                                Toast.makeText(RegisterActivity.this, "Utilizator creat cu succes", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Eroare la creearea contului", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
        radio_button_petsitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petsitter = true;
            }
        });
        radio_button_petsitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petsitter = false;
            }
        });
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    Picasso.get().load(uri).into(profileImage);

                    imgUri=uri;
                }
            });
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}