package com.example.bookingapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bookingapplication.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_account, R.id.navigation_notifications,R.id.reservationsFragment,
                R.id.addedPropertiesFragment,R.id.hostPropertiesFragment,R.id.commentsFragment,R.id.reportedUsersFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Intent intent = getIntent();
        String uloga = intent.getStringExtra("Uloga");
        switch (uloga) {
            case "Guest":
                Toast.makeText(getApplicationContext(), uloga, Toast.LENGTH_SHORT).show();
                navView.inflateMenu(R.menu.bottom_nav_menu_guest);
                break;
            case "Host":
                Toast.makeText(getApplicationContext(), uloga, Toast.LENGTH_SHORT).show();
                navView.inflateMenu(R.menu.bottom_nav_menu_host);
                break;
            case "Admin":
                Toast.makeText(getApplicationContext(), uloga, Toast.LENGTH_SHORT).show();
                navView.inflateMenu(R.menu.bottom_nav_menu_admin);
                break;
            default:
                break;
        }
    }
    

}