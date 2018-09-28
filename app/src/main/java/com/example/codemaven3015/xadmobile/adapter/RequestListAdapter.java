package com.example.codemaven3015.xadmobile.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.codemaven3015.xadmobile.Model.DonateModel;
import com.example.codemaven3015.xadmobile.Model.RequestModel;
import com.example.codemaven3015.xadmobile.R;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {
    Context context;
    //  String[] listDevises = {"Device1", "Devices2", "Devices3"};
    private ArrayList<RequestModel> requestList;


    public RequestListAdapter(Context context, ArrayList<RequestModel> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.response_list_card_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter.ViewHolder holder, int position) {
        RequestModel requestModel=requestList.get(position);
        holder.title_tv.setText(requestModel.getCategory_name());
        holder.title_tv1.setText(requestModel.getAdded_at());
        holder.title_tv2.setText(requestModel.getPatient_condition());
        holder.details_tv.setText(requestModel.getDescription());
        holder.status_tv.setText(requestModel.getStatus());
    }

    @Override
    public int getItemCount() {
        if(requestList !=null){
            return requestList.size();
        }else
        {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView title_tv, details_tv, status_tv,title_tv1,title_tv2;
        ImageView image;
        Button statusChange_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            title_tv1 = itemView.findViewById(R.id.title_tv1);
            title_tv2 = itemView.findViewById(R.id.title_tv2);
            details_tv = itemView.findViewById(R.id.details_tv);
            status_tv = itemView.findViewById(R.id.status_tv);

        }
    }
}

