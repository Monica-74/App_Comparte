package com.example.comparte.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
esta clase la hago porque voy a utilizar SQLite para guardar los datos, no necesito una gran base de datos, con lo que voy a utilizar
la base de datos local que trae el entorno de android.
y asi centralizo el acceso desde esta clase para evitar código duplicado, en otras clases.
 */
public class DatabaseManager {
    private static DBComparte dbHelper;
    private static SQLiteDatabase database;

    public static void init(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBComparte(context.getApplicationContext());
            database = dbHelper.getWritableDatabase(); // WritableDatabase me sirve para leer y escribir en la base de datos en modo escritura
        }

    }

    public static SQLiteDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("DatabaseManager no inicializada");

        }
        return database;
    }

    public static void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        database = null;
        dbHelper = null;
    }

    public static boolean login(String email, String password) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM usuarios WHERE email = ? AND password = ?",
                new String[]{email, password}
        );
        boolean loginExitoso = cursor.getCount() >0;
        cursor.close();
        database.close();
        return loginExitoso;

    }

    public static boolean registro(String email, String password) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM usuarios WHERE email = ? AND password = ?",
                new String[]{email, password}
        );
        boolean existeUsuario = cursor.getCount() >0;
        cursor.close();
        return existeUsuario;
    }

    public long insertarReserva(String nombre_inquilino, String descripcion_habitacion, String fecha_reserva, String telefono_inquilino, String email_inquilino) {

        ContentValues values = new ContentValues();
        values.put("nombre_inquilino", nombre_inquilino);
        values.put("descripcion_habitacion", descripcion_habitacion);
        values.put("fecha_reserva", fecha_reserva);
        values.put("telefono_inquilino", telefono_inquilino);
        values.put("email_inquilino", email_inquilino);
        return database.insert("reservas", null, values);
    }


}
