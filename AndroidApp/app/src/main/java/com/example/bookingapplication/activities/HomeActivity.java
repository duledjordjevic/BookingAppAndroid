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

        navView = findViewById(R.id.nav_view);
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
                setupBottomNavigationGuest();
                break;
            case "Host":
                Toast.makeText(getApplicationContext(), uloga, Toast.LENGTH_SHORT).show();
                setupBottomNavigationHost();
                break;
            case "Admin":
                Toast.makeText(getApplicationContext(), uloga, Toast.LENGTH_SHORT).show();
                setupBottomNavigationAdmin();
                break;
            default:
                break;
        }
    }

    private void setupBottomNavigationGuest() {
        Menu menu = navView.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home, Menu.NONE, "Home").setIcon(R.drawable.ic_home_black_24dp);
        menu.add(Menu.NONE, R.id.navigation_account, Menu.NONE, "Account").setIcon(R.drawable.ic_person);
        menu.add(Menu.NONE, R.id.navigation_notifications, Menu.NONE, "Notifications").setIcon(R.drawable.ic_notifications_black_24dp);;
        menu.add(Menu.NONE, R.id.reservationsFragment, Menu.NONE, "Reservations").setIcon(R.drawable.calendar);

        int initialItemId = determineInitialItemId();


        navView.setSelectedItemId(initialItemId);

    }
    private void setupBottomNavigationHost() {
        Menu menu = navView.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home, Menu.NONE, "").setIcon(R.drawable.ic_home_black_24dp);
        menu.add(Menu.NONE, R.id.navigation_account, Menu.NONE, "").setIcon(R.drawable.ic_person);
        menu.add(Menu.NONE, R.id.hostPropertiesFragment, Menu.NONE, "").setIcon(R.drawable.bed);
        menu.add(Menu.NONE, R.id.navigation_notifications, Menu.NONE, "").setIcon(R.drawable.ic_notifications_black_24dp);;
        menu.add(Menu.NONE, R.id.reservationsFragment, Menu.NONE, "").setIcon(R.drawable.calendar);

        int initialItemId = determineInitialItemId();


        navView.setSelectedItemId(initialItemId);

    }
    private void setupBottomNavigationAdmin() {
        Menu menu = navView.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home, Menu.NONE, "Account").setIcon(R.drawable.ic_person);
        menu.add(Menu.NONE, R.id.addedPropertiesFragment, Menu.NONE, "Properties").setIcon(R.drawable.bed);
        menu.add(Menu.NONE, R.id.reportedUsersFragment, Menu.NONE, "Reports").setIcon(R.drawable.flag);
        menu.add(Menu.NONE, R.id.commentsFragment, Menu.NONE, "Comments").setIcon(R.drawable.comment);;

        int initialItemId = determineInitialItemId();


        navView.setSelectedItemId(initialItemId);

    }

    private int determineInitialItemId() {
        // Dodajte logiku kako biste odredili koja stavka treba biti inicijalno označena
        // Na primjer, možete hardkodirati ID ili koristiti neku drugu logiku
        return R.id.navigation_home;
    }


}