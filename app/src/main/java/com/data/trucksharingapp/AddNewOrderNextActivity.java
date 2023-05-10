package com.data.trucksharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.data.trucksharingapp.data.DBHelper;
import com.data.trucksharingapp.databinding.ActivityAddNewOrderNextBinding;
import com.data.trucksharingapp.model.OrderModel;

import java.util.Objects;

public class AddNewOrderNextActivity extends AppCompatActivity {

    DBHelper dbHelper;

    String[] goodsTypes = {"Furniture", "Dry goods", "Food", "Building material"};
    String[] vehicleTypes ={"Truck", "Van", "Refrigerated truck", "Mini truck"};
    // https://developer.android.com/topic/libraries/view-binding
    public ActivityAddNewOrderNextBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewOrderNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String receiverName = intent.getStringExtra("receiverName");
        String pickupTime = intent.getStringExtra("pickupTime");
        String pickupDate = intent.getStringExtra("pickupDate");
        String pickupAddress = intent.getStringExtra("pickupAddress");
        String pickLat = intent.getStringExtra("pickLat");
        String pickLong = intent.getStringExtra("pickLong");
        String dropAddress = intent.getStringExtra("dropAddress");
        String dropLat = intent.getStringExtra("dropLat");
        String dropLong = intent.getStringExtra("dropLong");
        Long time_millis = intent.getLongExtra("time_millis", 0);
        Toast.makeText(this, pickLong, Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> goodsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goodsTypes);
        goodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vehicleTypes);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        binding.goodTypeSpinner.setAdapter(goodsAdapter);
        binding.vehicleTypeSpinner.setAdapter(vehicleAdapter);

        binding.createOrderBtn.setOnClickListener(view -> {
            String goodsType = binding.goodTypeSpinner.getSelectedItem().toString();
            String vehicleType = binding.vehicleTypeSpinner.getSelectedItem().toString();
            String weight = Objects.requireNonNull(binding.weightEdt.getText()).toString();
            String width = Objects.requireNonNull(binding.widthEdt.getText()).toString();
            String length = Objects.requireNonNull(binding.lengthEdt.getText()).toString();
            String height = Objects.requireNonNull(binding.heightEdt.getText()).toString();
            String quantity = Objects.requireNonNull(binding.quantityEdt.getText()).toString();

            if (goodsType.equals("") || vehicleType.equals("") || weight.equals("") || width.equals("") || length.equals("") || height.equals("")) {
                Toast.makeText(AddNewOrderNextActivity.this, "Please complete the data", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.addOrder(new OrderModel(receiverName, pickupTime, pickupDate, pickupAddress, pickLat, pickLong, dropAddress, dropLat, dropLong, goodsType, weight, length, width, height, quantity, vehicleType, time_millis));
                Toast.makeText(AddNewOrderNextActivity.this, " ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}