package com.example.comparte.activities;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comparte.R;


public class LoginActivity extends AppCompatActivity {


    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login); //

        NavHostFragment navHostFragment = null;
        if (navHostFragment == null) {
            throw new IllegalStateException("navHostFragment es null — ¿está bien tu layout?");
        }

        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_login);

        if (navHostFragment == null) {
            throw new IllegalStateException("navHostFragment es null — ¿está bien tu layout?");
        }

        navController = navHostFragment.getNavController();


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        Toast.makeText(this, "Se ve el fragmento directo", Toast.LENGTH_LONG).show();

    }

}
