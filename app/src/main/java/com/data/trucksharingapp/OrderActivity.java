package com.data.trucksharingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.data.trucksharingapp.data.DBHelper;
import com.data.trucksharingapp.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {

    public ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.orderRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.orderRecycler.setAdapter(new OrderAdapter(db.getAllOrders()));
    }
}