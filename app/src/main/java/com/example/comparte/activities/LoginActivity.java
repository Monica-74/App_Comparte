package com.example.comparte.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comparte.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LoginDebug", "LoginActivity iniciada");
        setContentView(R.layout.activity_login);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_login);

        if (navHostFragment == null) {
            throw new IllegalStateException("navHostFragment es null — revisa tu layout activity_login.xml");
        }

        NavController navController = navHostFragment.getNavController();

    }
}
