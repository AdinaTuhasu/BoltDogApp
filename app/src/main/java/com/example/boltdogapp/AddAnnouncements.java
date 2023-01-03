package com.example.boltdogapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.boltdogapp.model.Announcement;
import com.example.boltdogapp.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddAnnouncements extends AppCompatActivity {
    private DatabaseReference mydb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    StorageReference storageReference= FirebaseStorage.getInstance().getReference("petPhotos");

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

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_announcements,container,false);

        petName=view.findViewById(R.id.pet_name);
        petAddress=view.findViewById(R.id.pet_address);
        petBreed=view.findViewById(R.id.pet_breed);
        petAge=view.findViewById(R.id.pet_age);
        petDescription=view.findViewById(R.id.pet_description);
        imageView=view.findViewById(R.id.pet_image);
        imgUri=Uri.parse(imageView.toString());
        openCamera=view.findViewById(R.id.open_camera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                iCamera.setData(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            }
        });
        mydb.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addButton=view.findViewById(R.id.add_announce);
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

                mydb.child("announcements").child(user.getLastname()+" "+user.getFirstname()+" "+name).setValue(announcement);
                //Toast.makeText(getContext(), "Anunt adaugat cu succes!", Toast.LENGTH_SHORT).show();




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
        return view;
    }
}
