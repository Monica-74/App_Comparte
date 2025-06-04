package com.example.comparte.controller;


import android.content.Context;


import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Usuario;


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
    public Usuario login(String email, String password) {
        if (email == null || password == null) return null;

        // Normaliza para evitar errores por espacios o mayúsculas
        email = email.trim().toLowerCase();
        password = password.trim();

        Usuario usuario = dbComparte.obtenerUsuarioPorEmail(email);

        if (usuario != null && usuario.getPassword().trim().equals(password)) {
            return usuario;
        }
        return null;
    }

    public int obtenerIdPropietarioPorUsuario(int idUsuario) {
        return dbComparte.obtenerIdPropietarioPorUsuario(idUsuario);
    }
}
