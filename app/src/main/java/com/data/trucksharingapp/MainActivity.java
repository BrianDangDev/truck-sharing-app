package com.data.trucksharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.data.trucksharingapp.chatbot.ChatBotActivity;
import com.data.trucksharingapp.data.DBHelper;
import com.data.trucksharingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // https://developer.android.com/topic/libraries/data-binding/expressions
    public ActivityMainBinding binding;
    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);
        binding.newOrderBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNewOrderActivity.class);
            startActivity(intent);
        });

        if (dbHelper.getAllOrders().size() > 0){
            binding.newOrderTxt.setVisibility(View.VISIBLE);
            binding.newOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
            binding.newOrderRecycler.setAdapter(new NewOrderAdapter(dbHelper.getAllOrders()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.my_order_menu:
                startActivity(new Intent(this, OrderActivity.class));
                return true;
            case R.id.acounts_menu:
                startActivity(new Intent(this, ChatBotActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}