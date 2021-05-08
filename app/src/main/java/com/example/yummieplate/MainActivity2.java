package com.example.yummieplate;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_cake, R.id.navigation_pizza, R.id.navigation_sweets, R.id.navigation_cupcake, R.id.navigation_tea_n_cookies)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setItemIconTintList(null);

        int category = getIntent().getIntExtra("category", 1);
        Log.v("category", String.valueOf(category));
        if(category == 2){
            navController.navigate(R.id.action_navigation_cake_to_navigation_pizza);
        }
        else if(category == 3){
            navController.navigate(R.id.action_navigation_cake_to_navigation_sweets);
        }
        else if(category == 4){
            navController.navigate(R.id.action_navigation_cake_to_navigation_cupcake);
        }
        else if(category == 5){
            navController.navigate(R.id.action_navigation_cake_to_navigation_tea_n_cookies);
        }
    }

}