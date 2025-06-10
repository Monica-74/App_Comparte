package com.example.comparte.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Clase DatabaseManager
 *
 * Esta clase actúa como intermediaria entre la base de datos local (SQLite) y las diferentes capas
 * de la aplicación. Su función principal es gestionar todas las operaciones relacionadas con
 * el almacenamiento y recuperación de datos, como inserciones, consultas, actualizaciones y eliminaciones.
 *
 * Contiene métodos específicos para el manejo de entidades como usuarios, habitaciones, reservas,
 * chats u otras tablas del modelo de datos. También puede incluir lógica para la inicialización
 * de la base de datos y validaciones previas a las operaciones de escritura o lectura.
 *
 * Se utiliza principalmente para mantener separada la lógica de persistencia del resto de la aplicación,
 * siguiendo un enfoque modular y estructurado.
 */

public class DatabaseManager { // Clase para el rol de inquilino y propietario y administrador de la lógica de inicio de sesión
    private static DBComparte db; // Instancia de la base de datos
    private static SQLiteDatabase database; // Instancia de la base de datos

    public static void init(Context context) { // Método para inicializar la base de datos
        if (db == null) { // Verifica si la base de datos ya está inicializada
            db = new DBComparte(context.getApplicationContext()); // Crea una instancia de la base de datos si no está inicializada
            database = db.getWritableDatabase(); // WritableDatabase me sirve para leer y escribir en la base de datos en modo escritura
        }

    }

    public static SQLiteDatabase getDatabase() { // Método para obtener la instancia de la base de datos
        if (database == null) { // Verifica si la base de datos no está inicializada
            throw new IllegalStateException("DatabaseManager no inicializada"); // Lanza una excepción si la base de datos no está inicializada

        }
        return database; // Devuelve la instancia de la base de datos
    }

    public static void closeDatabase() { // Método para cerrar la base de datos
        if (database != null && database.isOpen()) { // Verifica si la base de datos está abierta
            database.close(); // Cierra la base de datos
        }
        database = null; // Establece la instancia de la base de datos en null
        db = null; // Establece la instancia de la base de datos en null
    }

    public static boolean login(String email, String password) { // Método para verificar las credenciales del usuario
        Cursor cursor = database.rawQuery( // Realiza una consulta para obtener el usuario asociado a las credenciales proporcionadas
                "SELECT * FROM usuarios WHERE email = ? AND password = ?", // Consulta SQL para buscar un usuario por email y contraseña en la tabla "usuarios" de la base de datos
                new String[]{email, password} // Parámetros de la consulta
        );
        boolean loginExitoso = cursor.getCount() >0; // Verifica si se encontraron resultados en la consulta (usuario encontrado)
        cursor.close(); // Cierra el cursor
        //database.close(); // Cierra la base de datos
        return loginExitoso; // Devuelve el resultado del inicio de sesión (éxito o fracaso)

    }

    public static boolean registro(String email, String password) { // Método para registrar un nuevo usuario en la base de datos
        Cursor cursor = database.rawQuery( // Realiza una consulta para verificar si el usuario ya existe en la base de datos
                "SELECT * FROM usuarios WHERE email = ? AND password = ?", // Consulta SQL para buscar un usuario por email y contraseña en la tabla "usuarios" de la base de datos
                new String[]{email, password} // Parámetros de la consulta
        );
        boolean existeUsuario = cursor.getCount() >0; // Verifica si se encontraron resultados en la consulta (usuario encontrado)
        cursor.close(); // Cierra el cursor
        return existeUsuario; // Devuelve el resultado del inicio de sesión (éxito o fracaso)
    }

    public long insertarReserva(String nombre_inquilino, String descripcion_habitacion, String fecha_reserva, String telefono_inquilino, String email_inquilino) { // Método para insertar una nueva reserva en la base de datos

        ContentValues values = new ContentValues(); // Crea un objeto ContentValues para almacenar los valores a insertar en la base de datos
        values.put("nombre_inquilino", nombre_inquilino); // Inserta el valor del nombre del inquilino en la columna "nombre_inquilino" de la tabla "reservas"
        values.put("descripcion_habitacion", descripcion_habitacion); // Inserta el valor de la descripción de la habitación en la columna "descripcion_habitacion" de la tabla "reservas"
        values.put("fecha_reserva", fecha_reserva); // Inserta el valor de la fecha de reserva en la columna "fecha_reserva" de la tabla "reservas"
        values.put("telefono_inquilino", telefono_inquilino); // Inserta el valor del teléfono del inquilino en la columna "telefono_inquilino" de la tabla "reservas"
        values.put("email_inquilino", email_inquilino); // Inserta el valor del email del inquilino en la columna "email_inquilino" de la tabla "reservas"
        return database.insert("reservas", null, values); // Inserta los valores en la tabla "reservas" de la base de datos y devuelve el ID generado
    }


}
