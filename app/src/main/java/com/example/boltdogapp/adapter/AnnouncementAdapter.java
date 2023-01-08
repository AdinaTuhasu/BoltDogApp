package com.example.boltdogapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.boltdogapp.petsitter.DetailAnnouncementActivity;
import com.example.boltdogapp.R;
import com.example.boltdogapp.model.Announcement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AnnouncementAdapter extends BaseAdapter {
    Button  btnView;
    TextView petName,ownerName, address;
    private ArrayList<Announcement> list = new ArrayList<Announcement>();
    private Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");

    public AnnouncementAdapter(ArrayList<Announcement> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public AnnouncementAdapter() {
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
        view=inflater.inflate(R.layout.announcement_layout,viewGroup,false);
        petName=view.findViewById(R.id.a_pet_name);
        ownerName=view.findViewById(R.id.a_owner_name);
        address=view.findViewById(R.id.a_address);
        btnView=view.findViewById(R.id.view_button);
        petName.setText(list.get(i).getName());
        ownerName.setText(list.get(i).getOwnername());
        address.setText(list.get(i).getAddress());

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Announcement an= list.get(i);
                Intent i=new Intent(context, DetailAnnouncementActivity.class);
                i.putExtra("announcement",an);
                context.startActivity(i);

            }
        });
        return view;


    }


}
