package com.example.comparte.controller;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Usuario;
import com.example.comparte.utils.CifradoContrasena;


public class LoginController {


    private final DBComparte dbComparte;
    private final Context context;


    public LoginController(Context context) {
        dbComparte = new DBComparte(context);
        this.context = context;


    }

    public boolean esAdministrador(String email, String password) {
        return email.equals("admin@comparte.com") && password.equals("admin"); // Verificar credenciales de administrador, sólo ese.
    }
//    public Usuario login(String email, String password) {
//        if (email == null || password == null) return null;
//
//        // Normaliza para evitar errores por espacios o mayúsculas
//        email = email.trim().toLowerCase();
//        password = password.trim();
//
//        Usuario usuario = dbComparte.obtenerUsuarioPorEmail(email);
//
//        if (usuario != null && usuario.getPassword().trim().equals(password)) {
//            return usuario;
//        }
//        return null;
//    }

    public int obtenerIdPropietarioPorUsuario(int idUsuario) {
        return dbComparte.obtenerIdPropietarioPorUsuario(idUsuario);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean login(String email, String password) {
        SQLiteDatabase db = dbComparte.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT password FROM usuarios WHERE email = ?", new String[]{email});
            if (cursor != null && cursor.moveToFirst()) {
                String storedHash = cursor.getString(0);

                // Volver a hashear la contraseña introducida
                String inputHash = CifradoContrasena.hashPassword(password);

                // Comparar hashes
                return storedHash.equals(inputHash);
            } else {
                return false; // usuario no encontrado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Usuario loginYObtenerUsuario(String email, String password) {
        if (login(email, password)) {
            return dbComparte.obtenerUsuarioPorEmail(email);
        }
        return null;
    }


}
