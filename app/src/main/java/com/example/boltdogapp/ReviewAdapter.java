package com.example.boltdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.boltdogapp.model.Announcement;
import com.example.boltdogapp.model.Review;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    Button btnView;
    TextView petsitterName,feedback;
    private ArrayList<Review> list = new ArrayList<Review>();
    private Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");

    public ReviewAdapter(ArrayList<Review> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ReviewAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.review_layout,viewGroup,false);
        petsitterName=view.findViewById(R.id.p_name);
        feedback=view.findViewById(R.id.feedback_petsitter);

        petsitterName.setText(list.get(i).getName());
        feedback.setText(list.get(i).getFeedback());


        return view;


    }


}