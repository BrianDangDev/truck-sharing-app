package com.data.trucksharingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.data.trucksharingapp.model.TruckModel;

import java.util.List;

public class TruckRecyclerAdapter extends RecyclerView.Adapter<TruckRecyclerAdapter.ViewHolder> {

    public List<TruckModel> truckModels;
    public TruckRecyclerAdapter(List<TruckModel> truckModels) {
        this.truckModels = truckModels;
    }

    @NonNull
    @Override
    public TruckRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruckRecyclerAdapter.ViewHolder holder, int position) {
        TruckModel truckModel = truckModels.get(position);
        holder.truckName.setText(truckModel.truck_name);
        holder.truckType.setText(truckModel.truck_type);
        holder.truckImg.setImageResource(truckModel.truck_img);
    }

    @Override
    public int getItemCount() {
        return truckModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView truckName;
        TextView truckType;
        ImageView truckImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            truckName = itemView.findViewById(R.id.truck_name_txt);
            truckType = itemView.findViewById(R.id.truck_type_txt);
            truckImg = itemView.findViewById(R.id.truck_img);
        }
    }
}
