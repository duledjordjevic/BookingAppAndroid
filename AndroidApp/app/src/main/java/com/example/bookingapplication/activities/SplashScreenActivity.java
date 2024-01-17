package com.example.bookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("share", "hello1");
        User user = SharedPreferencesManager.getUserInfo(getApplicationContext());
        Log.d("share", "hello2");
        if (user != null && !TextUtils.isEmpty(user.getEmail())) {
            Log.d("share", "hello3");
            Toast.makeText(SplashScreenActivity.this, "Successful log in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
            intent.putExtra("Role", user.getUserRole().toString());
            startActivity(intent);
            finish();
            return;
        }

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        splashScreen.setKeepOnScreenCondition(() -> true );
        setContentView(R.layout.activity_main);

        int SPLASH_TIME_OUT = 800;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}