package com.data.trucksharingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.data.trucksharingapp.directionhelpers.FetchURL;
import com.data.trucksharingapp.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class GetEstimate extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private Button payButton, feedBack;
    private int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(ConfigPaypal.PAYPAL_CLIENT_ID);
    GoogleMap map;
    MarkerOptions  pickupLocation, dropoffLocation;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_estimate);

        payButton = findViewById(R.id.payButton);
        feedBack = findViewById(R.id.feedBack);

        // https://developers.google.com/maps/documentation/directions/overview
        pickupLocation = new MarkerOptions().position(new LatLng(-6.175110, 106.865036)).title("Jakarta");
        dropoffLocation = new MarkerOptions().position(new LatLng(-6.917464, 107.619125)).title("Bandung");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        new FetchURL(GetEstimate.this).execute(getUrl(pickupLocation.getPosition(), dropoffLocation.getPosition(), "driving"), "driving");



        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaypalApiPayment();
            }
        });

        // feedback feature (voice-to-text)
        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetEstimate.this, feedbackPage.class);
                startActivity(intent);
            }
        });

    }

    // https://www.youtube.com/watch?v=wRDLjUK8nyU&t=1151s
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(pickupLocation);
        map.addMarker(dropoffLocation);
        // https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory
        LatLng camera = new LatLng(-6.175110, 106.865036);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 8), null);

    }

    // https://www.youtube.com/watch?v=wRDLjUK8nyU&t=1151s
    // https://developers.google.com/maps/documentation/directions/overview
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        // https://developers.google.com/maps/documentation/directions/overview
        String link = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return link;
    }

    // call driver button
    // https://www.programcreek.com/java-api-examples/?class=android.content.Intent&method=ACTION_CALL
    public void clickCallButton(View v){
        String phoneNumber = "5555555";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            // TODO: Consider calling
            return;
        }
        startActivity(callIntent);


    }


    // https://developer.paypal.com/limited-release/native-checkout/android/
    private void PaypalApiPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(80), "AUD", "Total Payment:", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    // https://developer.paypal.com/limited-release/native-checkout/android/
    @Override
    protected void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}
