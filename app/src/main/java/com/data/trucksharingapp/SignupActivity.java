package com.data.trucksharingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.data.trucksharingapp.data.DatabaseHelper;
import com.data.trucksharingapp.model.User;

public class SignupActivity extends AppCompatActivity {
    ImageView profilePicAdd;
    TextView addImage;
    DatabaseHelper db;

    // https://devofandroid.blogspot.com/2018/09/pick-image-from-gallery-android-studio.html
    private static final int PICK_IMG = 1000;
    private static final int PERMISSION = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profilePicAdd = findViewById(R.id.profilePicAdd);
        addImage = findViewById(R.id.addImage);

        EditText userName = findViewById(R.id.userName);
        EditText myPassword = findViewById(R.id.myPassword);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        Button button = findViewById(R.id.button);
        db = new DatabaseHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String password = myPassword.getText().toString();
                String confirm = confirmPassword.getText().toString();

                if (password.equals(confirm)){
                    long result = db.insertUser(new User(username, password));
                    if (result > 0){
                        Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Registered error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignupActivity.this, "please type the correct password", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // choose pic button
        // https://stackoverflow.com/questions/38352148/get-image-from-the-gallery-and-show-in-imageview
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });

        profilePicAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION);
                }
                else {
                    pickImageFromGallery();
                }
            }
            else {
                pickImageFromGallery();
            }
        }
    });
}



    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMG);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION:{
                // if user granted the permission, the app could access user's gallery
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery();
                }
                else
                // pops toast if the user denied the permission
                {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMG) {
            profilePicAdd.setImageURI(data.getData());
        }

    }
}
