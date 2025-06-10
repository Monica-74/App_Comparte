package com.example.comparte.activities;
/*
Clase MainActivity: creada para los roles de inquilino, propietario y administrador.
Actividad principal que gestiona la navegación entre fragmentos según el rol de usuario:
 * Inquilino
 * Propietario
 * Administrador

 */

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

public class MainActivity extends AppCompatActivity { // Clase para los roles de inquilino, propietario y administrador

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private SessionManager sessionManager;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;
    private boolean mostrarMenu = true;
    private NavController navController;

    @SuppressLint({"MissingInflatedId", "CommitPrefEdits"}) // Ignorar mensaje de advertencia
    @Override // Método que se ejecuta al crear la actividad
    protected void onCreate(Bundle savedInstanceState) { // Método que se ejecuta al crear la actividad
        super.onCreate(savedInstanceState); // Llama al método onCreate de la clase padre

        binding = ActivityMainBinding.inflate(getLayoutInflater()); // Infla el layout de la actividad
        setContentView(binding.getRoot()); // Establece el layout de la actividad

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // Oculta el teclado virtual


        drawerLayout = binding.drawerLayout; // Obtiene el DrawerLayout del layout
        navView = binding.navView; // Obtiene la NavigationView del layout

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        navController = navHostFragment.getNavController(); // Obtiene el NavHostFragment asociado al contenedor de fragmentos

        appBarConfiguration = new AppBarConfiguration.Builder( // Configura la navegación
                R.id.inquilinoFragment, R.id.adminFragment, R.id.propietarioFragment,R.id.loginFragment,R.id.registroFragment, R.id.reservaFragment) // Lista de fragmentos que se mostrarán en el Drawer (menu lateral)
                .setOpenableLayout(drawerLayout) // Configura el DrawerLayout para que se abra al abrir el menú
                .build(); // Configura la configuración del AppBarConfiguration

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); // Configura la navegación con el AppBarConfiguration y el NavController
        NavigationUI.setupWithNavController(navView, navController);


        preferences = getSharedPreferences("compArtePrefs", MODE_PRIVATE); // Obtiene las preferencias compartidas del usuario actual
        prefEditor = preferences.edit(); // Obtiene el editor de las preferencias compartidas

        sessionManager = new SessionManager(this); // Obtiene el gestor de sesión del usuario actual

        setupSession(); // Configura la sesión del usuario actual
        setupNavigation(); // Configura la navegación en la NavigationView
        setupDrawerLogout(); // Configura la navegación en el DrawerLayout

        String destino = getIntent().getStringExtra("fragmento_destino"); // Obtiene el destino del fragmento a cargar
        if ("reservas".equals(destino)) { // Si el destino es "reservas" carga el fragmento de reservas
            navController.navigate(R.id.reservaFragment); // Navega al fragmento de reservas
        }
    }

    @Override
    protected void onResume() { // Método que se ejecuta al volver a la actividad
        super.onResume();
    }

    private void setupSession() { // Configura la sesión del usuario actual
        sessionManager = new SessionManager(this);
    }

    private void setupNavigation() { // Configura la navegación en la NavigationView
        String rol = sessionManager.getUserRol(); // Obtiene el rol del usuario actual
        Log.d("MainActivity", "Rol recibido: " + rol); // Registra el rol recibido en el log
        Menu menu = navView.getMenu(); // Obtiene el menú de la NavigationView


        if ("admin".equals(rol)) { // Si el rol es "admin" muestra el botón de admin en el menú
            menu.findItem(R.id.adminFragment).setVisible("admin".equals(rol));
        }

        Toast.makeText(this, "Usuario con éxito: " + rol, Toast.LENGTH_LONG).show(); // Muestra un mensaje de éxito con el rol del usuario actual

        new android.os.Handler().postDelayed(() -> { // Espera un tiempo antes de navegar al fragmento correspondiente al rol del usuario actual
            switch (rol) { // Navega al fragmento correspondiente al rol del usuario actual
                case "admin": // Si el rol es "admin" navega al fragmento de admin
                    navController.navigate(R.id.adminFragment);
                    break;
                case "propietario": // Si el rol es "propietario" navega al fragmento de propietario
                    navController.navigate(R.id.propietarioFragment);
                    break;
                case "inquilino": // Si el rol es "inquilino" navega al fragmento de inquilino
                    navController.navigate(R.id.inquilinoFragment);
                    break;
                default:
                    Toast.makeText(this, "Rol no reconocido" + rol, Toast.LENGTH_SHORT).show(); // Muestra un mensaje de error si el rol no es reconocido
                    break;
            }
            Log.d("MainActivity", "Redirigiendo al fragmento según el rol: " + rol); // Registra el cambio de fragmento en el log

        }, 200); // Espera 200 milisegundos antes de navegar al fragmento correspondiente al rol del usuario actual



        navController.addOnDestinationChangedListener((controller, destination, args) -> { // Configura la navegación en el DrawerLayout
            boolean isLogin = destination.getId() == R.id.loginFragment; // Comprueba si el destino es el de login
            mostrarMenu = !isLogin;
            invalidateOptionsMenu();
            navView.setVisibility(isLogin ? View.GONE : View.VISIBLE); // Oculta la NavigationView si el destino es el de login
        });
    }

    private void setupDrawerLogout() { // Configura la navegación en el DrawerLayout
        navView.setNavigationItemSelectedListener(menuItem -> { // Configura la navegación en el DrawerLayout
            int itemId = menuItem.getItemId(); // Obtiene el id del item seleccionado en el DrawerLayout

            if (itemId == R.id.action_logout) { // Si el item seleccionado es el de logout
                sessionManager.logout(); // Cierra la sesión del usuario actual
                drawerLayout.closeDrawers(); // Cierra el DrawerLayout
                return true; // Devuelve true para indicar que se ha manejado la navegación
            } else if (itemId == R.id.nav_login_activity) { // Si el item seleccionado es el de login
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); // Crea un intent para la actividad de login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Establece las banderas del intent
                startActivity(intent); // Inicia la actividad de login
                finish(); // opcional: cerrar MainActivity para no volver atrás
                return true; // Devuelve true para indicar que se ha manejado la navegación
            } else { // Si el item seleccionado no es el de logout ni el de login
                NavigationUI.onNavDestinationSelected(menuItem, navController); // Navega al destino correspondiente al item seleccionado
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() { // Configura la navegación con el AppBarConfiguration y el NavController
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp(); // Navega al fragmento correspondiente al rol del usuario actual
    }
}