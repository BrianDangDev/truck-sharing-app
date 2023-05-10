package com.data.trucksharingapp.picklocation;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Geocoder;

import android.os.Bundle;
import android.widget.Toast;

import com.data.trucksharingapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.data.trucksharingapp.databinding.ActivityPickLocation2Binding;
import com.google.android.gms.tasks.CancellationTokenSource;

import java.io.IOException;
import java.util.Locale;

public class PickLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityPickLocation2Binding binding;
    public FusedLocationProviderClient fuseLoc;

    double lat = 0.0;
    double l0ng = 0.0;

    String lats = "";
    String longs = "";
    String addressLine = "";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPickLocation2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;

        fuseLoc = LocationServices.getFusedLocationProviderClient(this);

        CancellationTokenSource tokenSource = new CancellationTokenSource();
        fuseLoc.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, tokenSource.getToken()).addOnSuccessListener(location -> {
            lat = location.getLatitude();
            l0ng = location.getLongitude();

            mapFragment.getMapAsync(this);

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
            finish();
        });

        Intent currentIntent = getIntent();
        String request = currentIntent.getStringExtra("request");

        binding.submitBtn.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("address", addressLine);
            intent.putExtra("request", request);
            intent.putExtra("lat", lats);
            intent.putExtra("long", longs);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(lat, l0ng);
        mMap.addMarker(new MarkerOptions().position(currentLocation).draggable(true).title(currentLocation.toString()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 40f));

        mMap.getUiSettings().setZoomControlsEnabled(true);

        Geocoder geocoder = new Geocoder(PickLocationActivity.this, Locale.getDefault());
        try {
            android.location.Address address = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1).get(0);
            addressLine = address.getSubAdminArea() + ", " + address.getLocality() + ", " + address.getSubLocality();
            lats = String.valueOf(currentLocation.latitude);
            longs = String.valueOf(currentLocation.longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                LatLng latLng = marker.getPosition();
                Geocoder geocoder = new Geocoder(PickLocationActivity.this, Locale.getDefault());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                try {
                    android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
                    addressLine = address.getSubAdminArea() + ", " + address.getLocality() + ", " + address.getSubLocality();
                    lats = String.valueOf(latLng.latitude);
                    longs = String.valueOf(latLng.longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });
    }
}