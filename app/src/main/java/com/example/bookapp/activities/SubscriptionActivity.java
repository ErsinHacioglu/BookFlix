package com.example.bookapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.R;

public class SubscriptionActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button subscribeButton;
    private Button cancelSubscriptionButton;
    private TextView subscriptionStatus;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        subscribeButton = findViewById(R.id.subscribeButton);
        cancelSubscriptionButton = findViewById(R.id.cancelSubscriptionButton);
        subscriptionStatus = findViewById(R.id.subscriptionStatus);
        backButton = findViewById(R.id.backButton);

        checkSubscriptionStatus();

        subscribeButton.setOnClickListener(v -> {
            sharedPreferences.edit().putBoolean("isSubscribed", true).apply();
            checkSubscriptionStatus();
        });

        cancelSubscriptionButton.setOnClickListener(v -> {
            sharedPreferences.edit().putBoolean("isSubscribed", false).apply();
            checkSubscriptionStatus();
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void checkSubscriptionStatus() {
        boolean isSubscribed = sharedPreferences.getBoolean("isSubscribed", false);
        if (isSubscribed) {
            subscriptionStatus.setText("Abonelik Durumu: Aktif");
            subscribeButton.setVisibility(View.GONE);
            cancelSubscriptionButton.setVisibility(View.VISIBLE);
        } else {
            subscriptionStatus.setText("Abonelik Durumu: Aktif Değil");
            subscribeButton.setVisibility(View.VISIBLE);
            cancelSubscriptionButton.setVisibility(View.GONE);
        }
    }
}
