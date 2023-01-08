package com.example.boltdogapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boltdogapp.R;
import com.example.boltdogapp.model.Request;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatusRequestAdapter extends BaseAdapter {
    private ArrayList<Request> requestArrayList=new ArrayList<>();
    private Context context;
    TextView petname,ownername, status;
    public StatusRequestAdapter(ArrayList<Request> requestArrayList, Context context) {
        this.requestArrayList = requestArrayList;
        this.context = context;
    }

    public StatusRequestAdapter() {
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
        view=inflater.inflate(R.layout.status_request_layout,viewGroup,false);
        petname=view.findViewById(R.id.status_pet_name);
        ownername=view.findViewById(R.id.status_owner_name);
        status=view.findViewById(R.id.status);
        petname.setText(requestArrayList.get(i).getPetname());
        ownername.setText(requestArrayList.get(i).getOwnername());
        if(requestArrayList.get(i).getStatus().equals("In asteptare")){
            status.setTextColor(context.getResources().getColor(R.color.blue));
        }
        if(requestArrayList.get(i).getStatus().equals("Acceptata")){
            status.setTextColor(context.getResources().getColor(R.color.green));
        }
        if(requestArrayList.get(i).getStatus().equals("Respinsa")){
            status.setTextColor(context.getResources().getColor(R.color.red));
        }
        status.setText(requestArrayList.get(i).getStatus());
        return view;
    }
}
