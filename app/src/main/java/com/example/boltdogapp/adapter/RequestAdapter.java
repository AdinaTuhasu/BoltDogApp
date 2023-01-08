package com.example.boltdogapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boltdogapp.R;
import com.example.boltdogapp.model.Announcement;
import com.example.boltdogapp.model.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestAdapter extends BaseAdapter {
    private ArrayList<Request> requestArrayList=new ArrayList<>();
    private Context context;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boltdogapp-default-rtdb.firebaseio.com/");
    Button btn_accenpt, btn_reject, btn_profile;
    TextView petsitterName;
    public RequestAdapter(ArrayList<Request> requestArrayList, Context context) {
        this.requestArrayList = requestArrayList;
        this.context = context;
    }

    public RequestAdapter() {
    }


    @Override
    public int getCount() {
        return requestArrayList.size();
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
        view=inflater.inflate(R.layout.request_layout,viewGroup,false);
        petsitterName=view.findViewById(R.id.r_petsitter_name);
        petsitterName.setText(requestArrayList.get(i).getPetsitter());
        btn_accenpt=view.findViewById(R.id.accept_button);
        btn_reject=view.findViewById(R.id.reject_button);
        btn_profile=view.findViewById(R.id.profile_button);
        btn_accenpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("requests").child(requestArrayList.get(i).getOwnername()).child("status").setValue("Acceptata");
            }
        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("requests").child(requestArrayList.get(i).getOwnername()).child("status").setValue("Respinsa");

            }
        });
        return view;
    }
}
