package com.example.bookingapplication.activities;

import android.os.Bundle;
import android.view.Menu;

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

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void setupBottomNavigationGuest() {
        Menu menu = navView.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home, Menu.NONE, "Home").setIcon(R.drawable.ic_home_black_24dp);
        menu.add(Menu.NONE, R.id.navigation_dashboard, Menu.NONE, "Account").setIcon(R.drawable.ic_person);
        menu.add(Menu.NONE, R.id.navigation_notifications, Menu.NONE, "Notifications").setIcon(R.drawable.ic_notifications_black_24dp);;
        menu.add(Menu.NONE, R.id.reservationsFragment, Menu.NONE, "Reservations").setIcon(R.drawable.calendar);

        int initialItemId = determineInitialItemId();


        navView.setSelectedItemId(initialItemId);

    }
    private void setupBottomNavigationHost() {
        Menu menu = navView.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home, Menu.NONE, "").setIcon(R.drawable.ic_home_black_24dp);
        menu.add(Menu.NONE, R.id.navigation_dashboard, Menu.NONE, "").setIcon(R.drawable.ic_person);
        menu.add(Menu.NONE, R.id.navigation_dashboard, Menu.NONE, "").setIcon(R.drawable.bed);
        menu.add(Menu.NONE, R.id.navigation_notifications, Menu.NONE, "").setIcon(R.drawable.ic_notifications_black_24dp);;
        menu.add(Menu.NONE, R.id.reservationsFragment, Menu.NONE, "").setIcon(R.drawable.calendar);

        int initialItemId = determineInitialItemId();


        navView.setSelectedItemId(initialItemId);

    }
    private void setupBottomNavigationAdmin() {
        Menu menu = navView.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home, Menu.NONE, "Account").setIcon(R.drawable.ic_person);
        menu.add(Menu.NONE, R.id.navigation_dashboard, Menu.NONE, "Properties").setIcon(R.drawable.bed);
        menu.add(Menu.NONE, R.id.navigation_dashboard, Menu.NONE, "Reports").setIcon(R.drawable.flag);
        menu.add(Menu.NONE, R.id.navigation_notifications, Menu.NONE, "Comments").setIcon(R.drawable.comment);;

        int initialItemId = determineInitialItemId();


        navView.setSelectedItemId(initialItemId);

    }

    private int determineInitialItemId() {
        // Dodajte logiku kako biste odredili koja stavka treba biti inicijalno označena
        // Na primjer, možete hardkodirati ID ili koristiti neku drugu logiku
        return R.id.navigation_home;
    }


}