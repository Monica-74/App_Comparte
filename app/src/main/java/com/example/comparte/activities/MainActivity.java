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

        DatabaseManager.init(this);
        SQLiteDatabase db = DatabaseManager.getDatabase();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        drawerLayout = binding.drawerLayout;
        navView = binding.navView;
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        preferences = getSharedPreferences("compArtePrefs", MODE_PRIVATE);
        prefEditor = preferences.edit();

        sessionManager = new SessionManager(this);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inquilinoFragment, R.id.adminFragment, R.id.propietarioFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        setupSession();
        setupNavigation();
        setupDrawerLogout();
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
        Menu menu = navView.getMenu();


        if ("admin".equals(rol)) {
            menu.findItem(R.id.adminFragment).setVisible(true);
        }

        navView.post(() -> {
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
                    Toast.makeText(this, "Rol no reconocido" +rol, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

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
            } else {
                NavigationUI.onNavDestinationSelected(menuItem,
                        navController);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mostrarMenu) {
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

            String role = sessionManager.getUserRol();
            MenuItem adminItem = menu.findItem(R.id.adminFragment);
            if (adminItem != null) {
                adminItem.setVisible("admin".equals(role));
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            Drawable icon = item.getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(ContextCompat.getColor(this, R.color.colorSecundario), PorterDuff.Mode.SRC_IN);
                item.setIcon(icon);
            }

            SpannableString spanString = new SpannableString(item.getTitle());
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_text)),
                    0, spanString.length(), 0);
            item.setTitle(spanString);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            sessionManager.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}