package com.example.comparte.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction; // Necesario

import com.example.comparte.R;
import com.example.comparte.fragments.LoginFragment; // Necesario

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //

        if (savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.login_fragment_container, loginFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
