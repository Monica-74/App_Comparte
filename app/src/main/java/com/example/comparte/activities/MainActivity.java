package com.example.comparte.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comparte.R;
import com.example.comparte.database.DatabaseManager;
import com.example.comparte.databinding.ActivityMainBinding;
import com.example.comparte.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private SessionManager sessionManager;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;
    private boolean mostrarMenu = true;
    private NavController navController;

    @SuppressLint({"MissingInflatedId", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        drawerLayout = binding.drawerLayout;
        navView = binding.navView;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inquilinoFragment, R.id.adminFragment, R.id.propietarioFragment,R.id.loginFragment,R.id.registroFragment, R.id.reservaFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        preferences = getSharedPreferences("compArtePrefs", MODE_PRIVATE);
        prefEditor = preferences.edit();

        sessionManager = new SessionManager(this);

        setupSession();
        setupNavigation();
        setupDrawerLogout();

        String destino = getIntent().getStringExtra("fragmento_destino");
        if ("reservas".equals(destino)) {
            navController.navigate(R.id.reservaFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupSession() {
        sessionManager = new SessionManager(this);
    }

    private void setupNavigation() {
        String rol = sessionManager.getUserRol();
        //String rol = getIntent().getStringExtra("rol");
        Log.d("MainActivity", "Rol recibido: " + rol);
        Menu menu = navView.getMenu();


        if ("admin".equals(rol)) {
            menu.findItem(R.id.adminFragment).setVisible("admin".equals(rol));
        }

        Toast.makeText(this, "Usuario con éxito: " + rol, Toast.LENGTH_LONG).show();

        new android.os.Handler().postDelayed(() -> {
            //navView.post(() -> {
            switch (rol) {

                case "admin":
                    navController.navigate(R.id.adminFragment);
                    break;
                case "propietario":
                    navController.navigate(R.id.propietarioFragment);
                    break;
                case "inquilino":
                    navController.navigate(R.id.inquilinoFragment);
                    break;
                default:
                    Toast.makeText(this, "Rol no reconocido" + rol, Toast.LENGTH_SHORT).show();
                    break;
            }
            Log.d("MainActivity", "Redirigiendo al fragmento según el rol: " + rol);

        }, 200);



        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            boolean isLogin = destination.getId() == R.id.loginFragment;
            mostrarMenu = !isLogin;
            invalidateOptionsMenu();
            navView.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        });
    }

    private void setupDrawerLogout() {
        navView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.action_logout) {
                sessionManager.logout();
                drawerLayout.closeDrawers();
                return true;
            } else if (itemId == R.id.nav_login_activity) {
                // Ir manualmente a LoginActivity para reiniciar el flujo
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // opcional: cerrar MainActivity
                return true;
            } else {
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}