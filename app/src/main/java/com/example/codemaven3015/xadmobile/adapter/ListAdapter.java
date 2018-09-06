package com.example.codemaven3015.xadmobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codemaven3015.xadmobile.R;

import org.json.JSONArray;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    String [] listDevises = {"Device1","Devices2","Devices3"};

    public  ListAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_card_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.title.setText(listDevises[position]);
    }

    @Override
    public int getItemCount() {
        return listDevises.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView  title;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

        }
    }
}
