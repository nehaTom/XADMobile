package com.example.codemaven3015.xadmobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codemaven3015.xadmobile.Model.DonateModel;
import com.example.codemaven3015.xadmobile.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    String[] listDevises = {"Device1", "Devices2", "Devices3"};
    private ArrayList<DonateModel> donateList;

    public ListAdapter(Context context,ArrayList<DonateModel>donateList) {
        this.context = context;
          this.donateList=donateList;

    }


    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_card_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        DonateModel donateModel=donateList.get(position);
      //  holder.title_tv.setText(listDevises[position]);
        holder.title_tv.setText(donateModel.getCategory_name());
        holder.details_tv.setText(donateModel.getAdded_at());
        holder.status_tv.setText(donateModel.getWorking_status());
    }

    @Override
    public int getItemCount() {
        if(donateList !=null){
            return donateList.size();
        }else
        {
            return 0;
        }
       // return listDevises.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_tv, details_tv, status_tv;
        ImageView image;
        Button statusChange_btn;


        public ViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            details_tv = itemView.findViewById(R.id.details_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
            image = itemView.findViewById(R.id.image);
            statusChange_btn = itemView.findViewById(R.id.statusChange_btn);
        }
    }
}
