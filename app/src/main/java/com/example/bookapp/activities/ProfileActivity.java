package com.example.bookapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private Button logoutButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameTextView = findViewById(R.id.userNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);
        logoutButton = findViewById(R.id.logoutButton);
        backButton = findViewById(R.id.backButton);

        loadUserProfile();

        logoutButton.setOnClickListener(v -> {
            clearUserSession();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });

        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void loadUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "Bilinmeyen Kullanıcı");
        String userEmail = sharedPreferences.getString("userEmail", "Bilinmeyen E-posta");

        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
