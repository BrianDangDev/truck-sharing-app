package com.data.trucksharingapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.data.trucksharingapp.model.OrderModel;

import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    public List<OrderModel> orderModels;

    public NewOrderAdapter(List<OrderModel> orderModels) {
        this.orderModels = orderModels;
    }

    @NonNull
    @Override
    public NewOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewOrderAdapter.ViewHolder holder, int position) {
        OrderModel orderModel = orderModels.get(position);
        holder.vehicleType.setText(orderModel.vehicle_type);
        holder.goodsType.setText(orderModel.goods_type);


        holder.shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailOrderActivity.class);
                intent.putExtra("id", orderModel.id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView vehicleType;
        TextView goodsType;
        CardView orderCard;
        ImageView shareImg;

        public ViewHolder(@NonNull View parent) {
            super(parent);
            vehicleType = parent.findViewById(R.id.vehicle_type_txt);
            goodsType = parent.findViewById(R.id.goods_type_txt);
            orderCard = parent.findViewById(R.id.orderCard);
            shareImg = parent.findViewById(R.id.shareImg);
        }
    }
}
