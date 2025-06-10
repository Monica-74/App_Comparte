package com.example.comparte.controller;
/*
/**
 * Clase LoginController
 *
 * Esta clase gestiona la lógica de autenticación de usuarios dentro de la aplicación.
 * Se encarga de validar las credenciales introducidas durante el inicio de sesión,
 * comprobando que el usuario exista en la base de datos y que la contraseña introducida coincida
 * con la contraseña almacenada, la cual ha sido cifrada previamente.
 *
 * Funciona como intermediaria entre la vista (LoginActivity o LoginFragment) y la capa
 * de persistencia (DatabaseManager o DBComparte), encapsulando la lógica de negocio relacionada
 * con el proceso de login.
 *
 * Además, puede incluir métodos auxiliares para gestionar sesiones, recordar usuarios
 * o diferenciar entre roles (administrador, propietario, inquilino, etc.).
 */


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Usuario;
import com.example.comparte.utils.CifradoContrasena;


public class LoginController { // Clase para el rol de inquilino y propietario y administrador de la lógica de inicio de sesión


    private final DBComparte dbComparte;
    private final Context context;


    public LoginController(Context context) { // Constructor de la clase LoginController que recibe el contexto de la aplicación
        dbComparte = new DBComparte(context); // Crea una instancia de la base de datos
        this.context = context; // Guarda el contexto de la aplicación


    }

    public boolean esAdministrador(String email, String password) { // Método para verificar si el usuario es administrador
        return email.equals("admin@comparte.com") && password.equals("admin"); // Verificar credenciales de administrador, sólo ese.
    }

    public int obtenerIdPropietarioPorUsuario(int idUsuario) { // Método para obtener el ID del propietario asociado a un usuario
        return dbComparte.obtenerIdPropietarioPorUsuario(idUsuario); // Llama al método correspondiente en la base de datos
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // Anotación para indicar que se requiere una versión de API específica
    public boolean login(String email, String password) { // Método para verificar las credenciales del usuario
        SQLiteDatabase db = dbComparte.getReadableDatabase(); // Abre la base de datos en modo de lectura
        Cursor cursor = null; // Cursor para recorrer los resultados de la consulta

        try {
            cursor = db.rawQuery("SELECT password FROM usuarios WHERE email = ?", new String[]{email}); // Realiza una consulta para obtener la contraseña del usuario
            if (cursor != null && cursor.moveToFirst()) { // Verifica si se encontraron resultados
                String storedHash = cursor.getString(0); // Obtiene la contraseña almacenada

                String inputHash = CifradoContrasena.hashPassword(password); // Hashea la contraseña introducida

                return storedHash.equals(inputHash); // Compara las contraseñas hasheadas
            } else {
                return false; // usuario no encontrado
            }
        } catch (Exception e) { // Manejo de excepciones
            e.printStackTrace(); // Imprime el mensaje de error
            return false; // Error al acceder a la base de datos
        } finally { // Cierre del cursor y la base de datos
            if (cursor != null) cursor.close(); // Cierra el cursor
            db.close(); // Cierra la base de datos
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // Anotación para indicar que se requiere una versión de API específica para el método
    public Usuario loginYObtenerUsuario(String email, String password) { // Método para iniciar sesión y obtener el usuario asociado a las credenciales proporcionadas
        if (login(email, password)) { // Verifica las credenciales
            return dbComparte.obtenerUsuarioPorEmail(email); // Obtiene el usuario asociado a las credenciales proporcionadas
        }
        return null;
    }


}
