package com.example.comparte.activities;
/*
Clase Login Activity: creada para el rol de inquilino.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comparte.R;

public class LoginActivity extends AppCompatActivity { // Clase para el rol de inquilino


    @Override
    protected void onCreate(Bundle savedInstanceState) { //
        setTheme(R.style.Theme_Comparte); // Establece el tema de la actividad

        super.onCreate(savedInstanceState); // Llama al método onCreate de la clase padre
        Log.d("LoginDebug", "LoginActivity iniciada"); // Registra un mensaje de depuración
        setContentView(R.layout.activity_login); // Establece el layout de la actividad

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_login); // Obtiene el NavHostFragment asociado al contenedor de fragmentos

        if (navHostFragment == null) { // Comprueba si el NavHostFragment es nulo (no se ha encontrado)
            throw new IllegalStateException("navHostFragment es null — revisa tu layout activity_login.xml");
        }

        NavController navController = navHostFragment.getNavController(); // Obtiene el NavController asociado al NavHostFragment
    }
}
