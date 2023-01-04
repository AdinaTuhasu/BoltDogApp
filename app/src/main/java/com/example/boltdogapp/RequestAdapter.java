package com.example.boltdogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.boltdogapp.model.Request;

import java.util.ArrayList;

public class RequestAdapter extends BaseAdapter {
    private ArrayList<Request> requestArrayList=new ArrayList<>();
    private Context context;

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
        return view;
    }
}
