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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
//    TextView textView5;

    public List<OrderModel> orderModels;

    public OrderAdapter(List<OrderModel> orderModels) {
        this.orderModels = orderModels;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderModel orderModel = orderModels.get(position);
        holder.receiverName.setText(orderModel.receiver_name);
        holder.pickupTime.setText(orderModel.pickup_time);

        // details
        holder.orderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailOrderActivity.class);
                intent.putExtra("id", orderModel.id);
                view.getContext().startActivity(intent);
            }
        });

        // share to social media
        holder.shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to the order detail page
                Intent intentpage = new Intent(view.getContext(), DetailOrderActivity.class);
                intentpage.putExtra("id", orderModel.id);
                view.getContext().startActivity(intentpage);

                // share to social media
                // https://developer.android.com/training/sharing/send
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "test");
                intent.setType("text/plain");
                String x = " ";
                intent.putExtra(Intent.EXTRA_TEXT,x);
                view.getContext().startActivity(Intent.createChooser(intent, "share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView receiverName;
        TextView pickupTime;
        CardView orderCard;
        ImageView shareImg;

        public ViewHolder(@NonNull View parent) {
            super(parent);
            receiverName = parent.findViewById(R.id.order_receiver_name);
            pickupTime = parent.findViewById(R.id.pick_up_time);
            orderCard = parent.findViewById(R.id.orderCard);
            shareImg = parent.findViewById(R.id.shareImg);
        }
    }
}
