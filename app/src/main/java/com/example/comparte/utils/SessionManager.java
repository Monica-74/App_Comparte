package com.example.comparte.utils;
/*
para guardar y accceder facilmente a datos del usuario en sesión
 */

import android.content.Context;
import android.content.SharedPreferences;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.comparte.R;

public class SessionManager {

    private final SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "userSession";
    private static final String KEY_ROLE = "user_role";

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void logout() {
        prefs.edit().remove("token").apply();
        // Nota: Navegación debe hacerse desde un NavController con contexto si quieres mover esta parte aquí.
        // Puedes dejar la navegación en MainActivity si es más sencillo.
    }
    public  void saveUserRole(String role) {
        editor.putString(KEY_ROLE, role);
        editor.apply();

    }

    public String getUserRole() {
        return prefs.getString(KEY_ROLE, null);
    }
    public void clearSession() {
        editor.clear();
        editor.apply();
    }

}
