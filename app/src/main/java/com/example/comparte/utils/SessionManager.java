package com.example.comparte.utils;
/*
para guardar y accceder facilmente a datos del usuario en sesión
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private final SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "userSession";
    private static final String KEY_ROL = "user_rol";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_PROPIETARIO_ID = "propietario_id";


    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void logout() {
        prefs.edit().remove("token").apply();

    }
    public  void saveUserRole(String role) {
        editor.putString(KEY_ROL, role);
        editor.apply();

    }

    public String getUserRol() {

        return prefs.getString(KEY_ROL, null);
    }
    public void clearSession() {
        editor.clear();
        editor.apply();
    }

    public void setUserRol(String rol) {
        editor.putString(KEY_ROL, rol);
        editor.apply(); // o editor.commit();
    }

    // Guardar ID del usuario
    public void saveUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1); // -1 si no existe
    }

    // Guardar ID del propietario
    public void savePropietarioId(int propietarioId) {
        editor.putInt(KEY_PROPIETARIO_ID, propietarioId);
        editor.apply();
    }

    public int getPropietarioId() {
        return prefs.getInt(KEY_PROPIETARIO_ID, -1); // -1 si no existe
    }
}
