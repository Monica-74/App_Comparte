package com.example.comparte.controllers;


import android.content.Context;


import com.example.comparte.database.DBComparte;
import com.example.comparte.models.Admin;
import com.example.comparte.models.Usuario;


public class LoginController {


    private final DBComparte dbComparte;



    public LoginController(Context context) {
        dbComparte = new DBComparte(context);


    }


    public boolean login(String email, String password) {
        Usuario usuario = dbComparte.obtenerUsuarioPorEmail(email);
        return usuario != null && usuario.getpassword().equals(password);


    }


    public boolean esAdministrador(String email, String password){
        Admin admin = new Admin();
        return email.equals(admin.getEmail()) && password.equals(admin.getPassword());
    }
}
