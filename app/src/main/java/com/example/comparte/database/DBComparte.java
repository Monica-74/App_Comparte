package com.example.comparte.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.comparte.models.Usuario;

public class DBComparte extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "comparte.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_HABITACIONES = "habitaciones";
    public static final String TABLE_CHAT = "chat";
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_INQUILINO = "inquilino";
    public static final String TABLE_PROPIETARIO = "propietario";
    public static final String TABLE_RESERVA = "reserva";

    public DBComparte(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tabla usuarios
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USUARIOS + " (" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_usuario TEXT," +
                "password TEXT," +
                "rol TEXT," +
                "telefono TEXT," +
                "genero TEXT," +
                "email TEXT," +
                "edad TEXT" +
                ")");

        // Tabla admin (información extra)
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADMIN + " (" +
                "id_admin INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "email TEXT," +
                "id_inquilino INTEGER ,"+
                "id_propietario INTEGER ,"+
                "FOREIGN KEY(id_inquilino) REFERENCES inquilinos(id_inquilino)," +
                "FOREIGN KEY(id_propietario) REFERENCES propietarios(id_propietario)," +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones (id_habitacion)" +
                ")");

        // Tabla inquilino (información extra)
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_INQUILINO + " (" +
                "id_inquilino INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "edad TEXT," +
                "email TEXT," +
                "telefono TEXT," +
                "sexo TEXT," +
                "telefono TEXT," +
                "id_propietario INTEGER ,"+
                "id_habitacion  INTEGER ,"+
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones (id_habitacion)," +
                "FOREIGN KEY(id_propietario) REFERENCES propietarios (id_propietario)" +
                ")");

        // Tabla propietario (información extra)
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROPIETARIO + " (" +
                "id_propietario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "email TEXT," +
                "edad TEXT," +
                "telefono TEXT," +
                "sexo TEXT," +
                "id_inquilino INTEGER ,"+
                "id_habitacion  INTEGER ,"+
                "FOREIGN KEY(id_inquilino) REFERENCES inquilinos(id_inquilino)," +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones(id_habitacion)" +
                ")");

        // Tabla habitaciones
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HABITACIONES + " (" +
                "id_habitacion INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "descripcion TEXT," +
                "precio TEXT," +
                "direccion TEXT," +
                "imagen BLOB," +
                "tipo TEXT," +
                "id_propietario INTEGER," +
                "id_inquilino INTEGER ,"+
                "FOREIGN KEY(id_propietario) REFERENCES propietario(id_propietario)," +
                "FOREIGN KEY(id_inquilino) REFERENCES inquilinos(id_inquilino)" +
                ")");

        // Tabla chat
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CHAT + " (" +
                "id_chat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mensaje TEXT," +
                "fecha INTEGER, " +
                "id_propietario INTEGER," +
                "id_inquilino INTEGER," +
                "id_habitacion INTEGER," +
                "FOREIGN KEY(id_propietario) REFERENCES propietario(id_propietario)," +
                "FOREIGN KEY(id_inquilino) REFERENCES inquilino(id_inquilino)," +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones(id_habitacion)" +
                ")");

        //Tabla reservas
        db.execSQL("CREATE TABLE IF NOT EXISTS " +  TABLE_RESERVA + "(" +
                "id_reserva INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_inquilino TEXT," +
                "descripcion_habitacion TEXT," +
                "fecha_reserva TEXT," +
                "telefono_inquilino TEXT," +
                "email_inquilino TEXT," +
                "id_inquilino INTEGER," +
                "id_habitacion INTEGER," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "FOREIGN KEY(id_inquilino) REFERENCES inquilino(id_inquilino)," +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones(id_habitacion)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITACIONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INQUILINO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPIETARIO);
        onCreate(db);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE email = ?", new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario")));
            usuario.setNombreUsuario(cursor.getString(cursor.getColumnIndexOrThrow("nombre_usuario")));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            usuario.setRol(cursor.getString(cursor.getColumnIndexOrThrow("rol")));
            // Agregá otros setters si es necesario
            cursor.close();
            return usuario;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    public boolean insertarUsuario(Usuario usuario) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "INSERT INTO " + TABLE_USUARIOS + " (nombre_usuario, password, rol, telefono, genero, email, edad) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            db.execSQL(sql, new Object[]{
                    usuario.getNombreUsuario(),
                    usuario.getPassword(),
                    usuario.getRol(),
                    usuario.getTelefono(),
                    usuario.getGenero(),
                    usuario.getEmail(),
                    usuario.getEdad()
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
