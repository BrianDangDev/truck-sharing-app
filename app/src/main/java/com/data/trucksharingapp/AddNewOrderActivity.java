package com.data.trucksharingapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.data.trucksharingapp.data.DBHelper;
import com.data.trucksharingapp.databinding.ActivityAddNewOrderBinding;
import com.data.trucksharingapp.picklocation.PickLocationActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddNewOrderActivity extends AppCompatActivity {

    public ActivityAddNewOrderBinding binding;
    public DBHelper dbHelper;
    public Button button2;

    // Calendar
    // https://developer.android.com/reference/java/util/Calendar
    final Calendar myCalendar = Calendar.getInstance();

    String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public ActivityResultLauncher<String[]> resultLauncher;
    public ActivityResultLauncher<Intent> intentResult;

    String pick_lats = "";
    String pick_longs = "";
    String pick_addressLine = "";

    String drop_lats = "";
    String drop_longs = "";
    String drop_addressLine = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);
        button2 = findViewById(R.id.button2);


        // https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
            binding.dateEdt.setText(dateFormat.format(myCalendar.getTime()));
        };

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNewOrderActivity.this, "set", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewOrderActivity.this, broadCast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNewOrderActivity.this, 0, intent,0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                long timeAtButtonClick = System.currentTimeMillis();
                // 1 minute before truck's arrival
                long tenSecondsInMillis = 1000 * 60;

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondsInMillis, pendingIntent);
            }
        });



        // Time
        // https://stackoverflow.com/questions/11653818/how-to-get-day-month-and-year-from-datepickerdialog
        TimePickerDialog.OnTimeSetListener time = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            binding.timeEdt.setText(hourOfDay + ":" + minute);
        };

        resultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION)) && Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_COARSE_LOCATION))) {
                Intent intent = new Intent(this, PickLocationActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });

        intentResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();
            assert data != null;

            if (Objects.equals(data.getStringExtra("request"), "pickup")){
                pick_addressLine = data.getStringExtra("address");
                pick_lats = data.getStringExtra("lat");
                pick_longs = data.getStringExtra("long");
                binding.pickUp.setText(pick_addressLine);
            } else {
                drop_addressLine = data.getStringExtra("address");
                drop_lats = data.getStringExtra("lat");
                drop_longs = data.getStringExtra("long");
                binding.dropOff.setText(drop_addressLine);
            }
        });

        // date from user input
        binding.dateImg.setOnClickListener(view -> new DatePickerDialog(AddNewOrderActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        // time from user input
        binding.timeImg.setOnClickListener(view -> new TimePickerDialog(AddNewOrderActivity.this, time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show());

        // go to the next page
        binding.nextBtn.setOnClickListener(view -> {
            String receiverName = Objects.requireNonNull(binding.receiverEdt.getText()).toString();
            String pickupDate = Objects.requireNonNull(binding.dateEdt.getText()).toString();
            String pickupTime = Objects.requireNonNull(binding.timeEdt.getText()).toString();
            String pickupAddress = Objects.requireNonNull(binding.pickUp.getText()).toString();
            String dropoffAddress = Objects.requireNonNull(binding.dropOff.getText()).toString();


            // all data should be filled-in, otherwise toast will be prompted
            if (receiverName.isEmpty() || pickupDate.isEmpty() || pickupTime.isEmpty() || pickupAddress.isEmpty() || dropoffAddress.isEmpty()) {
                Toast.makeText(AddNewOrderActivity.this, "Data isn't complete yet", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(AddNewOrderActivity.this, AddNewOrderNextActivity.class);
                intent.putExtra("receiverName", receiverName);
                intent.putExtra("pickupDate", pickupDate);
                intent.putExtra("pickupTime", pickupTime);
                intent.putExtra("pickupAddress", pickupAddress);
                intent.putExtra("pickLat", pick_lats);
                intent.putExtra("pickLong", pick_longs);
                intent.putExtra("dropAddress", dropoffAddress);
                intent.putExtra("dropLat", drop_lats);
                intent.putExtra("dropLong", drop_longs);
                intent.putExtra("time_millis", myCalendar.getTimeInMillis());
                startActivity(intent);
                finish();
            }
        });

        binding.pickUpImg.setOnClickListener(v -> {

            if (checkLoc1() && checkLoc2()) {
                Intent intent = new Intent(this, PickLocationActivity.class);
                intent.putExtra("request", "pickup");
                intentResult.launch(intent);
            } else {
                resultLauncher.launch(permission);
            }
        });

        binding.dropImg.setOnClickListener(v -> {
            if (checkLoc1() && checkLoc2()) {
                Intent intent = new Intent(this, PickLocationActivity.class);
                intent.putExtra("request", "dropoff");
                intentResult.launch(intent);
            } else {
                resultLauncher.launch(permission);
            }
        });
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder! Your truck is on it's way";
            String description = "Please prepare your items";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("PostCode",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public Boolean checkLoc1() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public Boolean checkLoc2() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}