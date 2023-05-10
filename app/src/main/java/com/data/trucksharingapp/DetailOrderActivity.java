package com.data.trucksharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.data.trucksharingapp.data.DBHelper;
import com.data.trucksharingapp.databinding.ActivityDetailOrderBinding;
import com.data.trucksharingapp.model.OrderModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailOrderActivity extends AppCompatActivity {
    public ActivityDetailOrderBinding binding;
    public Button estimate_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        OrderModel order = db.getOrder(id);

        Long time_milis = order.time_millis + 10800000;
        estimate_btn = findViewById(R.id.estimate_btn);
        binding.receiverNameTxt.setText(order.receiver_name);
        binding.timeTxt.setText("Pickup Time: " + order.pickup_time);
        binding.dropOffTimeTxt.setText("Dropoff Time: " + getTime(time_milis));
        binding.weightTxt.setText(order.weight + " kg");
        binding.typeTxt.setText(order.goods_type);
        binding.widthTxt.setText(order.width + " cm");
        binding.heightTxt.setText(order.height + " cm");
        binding.lengthTxt.setText(order.length + " cm");
        binding.quantityTxt.setText(order.quantity);

        Log.d("or2", order.pick_long);

        estimate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailOrderActivity.this, GetEstimate.class);
                startActivity(intent);
            }
        });
    }

    public String getTime(Long timeMilis) {
        DateFormat formatter = new SimpleDateFormat("HH:mm", Locale.US);
        return formatter.format(new Date(timeMilis));
    }
}