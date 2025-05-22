package com.example.comparte.controller;


import android.content.Context;


import com.example.comparte.database.DBComparte;
import com.example.comparte.models.Admin;
import com.example.comparte.models.Usuario;


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
        Usuario usuario = dbComparte.obtenerUsuarioPorEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}
