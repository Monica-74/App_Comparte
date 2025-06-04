package com.example.comparte.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.comparte.entities.Habitacion;
import com.example.comparte.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DBComparte extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "comparte.db";
    private static final int DATABASE_VERSION = 16;

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
                "FOREIGN KEY(id_propietario) REFERENCES propietarios(id_propietario)" +
                ")");

        // Tabla inquilino (información extra)
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_INQUILINO + " (" +
                "id_inquilino INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "edad TEXT," +
                "email TEXT," +
                "telefono TEXT," +
                "sexo TEXT," +
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
                "id_usuario INTEGER ,"+
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
                "caracteristica_cama TEXT," +
                "caracteristica_bano TEXT," +
                "caracteristica_tamano TEXT," +
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
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITACIONES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_INQUILINO);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPIETARIO);
        onCreate(database);
    }


    public Usuario obtenerUsuarioPorEmail(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM usuarios WHERE email = ?", new String[]{email.toLowerCase()});

        if (cursor != null && cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario")));
            usuario.setNombreUsuario(cursor.getString(cursor.getColumnIndexOrThrow("nombre_usuario")));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            usuario.setRol(cursor.getString(cursor.getColumnIndexOrThrow("rol")));
            // Agregá otros setters si es necesario


            String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"));
            usuario.setRol(rol != null ? rol.toLowerCase() : "");

            cursor.close();
            return usuario;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    public boolean insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (db != null) {
                db.beginTransaction();

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

                String rol = usuario.getRol().toLowerCase();
                if (rol.equals("propietario")) {
                    sql = "INSERT INTO " + TABLE_PROPIETARIO + " (nombre_apellidos, email, edad, telefono, sexo) VALUES (?, ?, ?, ?, ?)";
                    db.execSQL(sql, new Object[]{
                            usuario.getNombreUsuario(), usuario.getEmail(), usuario.getEdad(), usuario.getTelefono(), usuario.getGenero()
                    });
                } else if (rol.equals("inquilino")) {
                    sql = "INSERT INTO " + TABLE_INQUILINO + " (nombre_apellidos, edad, email, telefono, sexo) VALUES (?, ?, ?, ?, ?)";
                    db.execSQL(sql, new Object[]{
                            usuario.getNombreUsuario(), usuario.getEdad(), usuario.getEmail(), usuario.getTelefono(), usuario.getGenero()
                    });
                } else if (rol.equals("admin")) {
                    sql = "INSERT INTO " + TABLE_ADMIN + " (nombre_apellidos, email) VALUES (?, ?)";
                    db.execSQL(sql, new Object[]{
                            usuario.getNombreUsuario(), usuario.getEmail()
                    });
                }

                db.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) {
                db.endTransaction();
            }
        }
    }


    public int obtenerIdPropietarioPorUsuario(int idUsuario) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int idPropietario = -1;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT id_propietario FROM propietario WHERE id_usuario = ?",
                    new String[]{String.valueOf(idUsuario)}
            );

            if (cursor.moveToFirst()) {
                idPropietario = cursor.getInt(cursor.getColumnIndexOrThrow("id_propietario"));
            }

        } catch (Exception e) {
            e.printStackTrace(); // para ver el error si ocurre
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return idPropietario;
    }



    public List<Habitacion> getHabitacionesPorPropietario(int idPropietario) {
        List<Habitacion> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM habitaciones WHERE id_propietario = ?", new String[]{String.valueOf(idPropietario)});

        if (cursor.moveToFirst()) {
            do {
                Habitacion h = new Habitacion();
                h.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_habitacion")));
                h.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                h.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                h.setPrecio(cursor.getString(cursor.getColumnIndexOrThrow("precio")));
                h.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow("direccion")));
                h.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
                h.setCaracteristicaCama(cursor.getString(cursor.getColumnIndexOrThrow("caracteristica_cama")));
                h.setCaracteristicaBano(cursor.getString(cursor.getColumnIndexOrThrow("caracteristica_bano")));
                h.setCaracteristicaTamano(cursor.getString(cursor.getColumnIndexOrThrow("caracteristica_tamano")));
                h.setImagen(cursor.getBlob(cursor.getColumnIndexOrThrow("imagen")));
                // Puedes cargar otras características si las tienes en columnas separadas
                lista.add(h);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public void eliminarHabitacion(int idHabitacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("habitaciones", "id_habitacion = ?", new String[]{String.valueOf(idHabitacion)});
    }

    public void actualizarHabitacion(Habitacion h) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", h.getTitulo());
        values.put("descripcion", h.getDescripcion());
        values.put("precio", h.getPrecio());
        values.put("direccion", h.getDireccion());
        values.put("imagen", h.getImagen());
        values.put("tipo", h.getTipo());
        values.put("caracteristica_cama", h.getCaracteristicaCama());
        values.put("caracteristica_bano", h.getCaracteristicaBano());
        values.put("caracteristica_tamano", h.getCaracteristicaTamano());

        db.update("habitaciones", values, "id_habitacion = ?", new String[]{String.valueOf(h.getId())});
    }




    public void insertarHabitacion(Habitacion h) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO habitaciones (titulo, descripcion, precio, direccion, imagen, tipo, " +
                "caracteristica_cama, caracteristica_bano, caracteristica_tamano, id_propietario, id_inquilino) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";

        db.execSQL(sql, new Object[]{
                h.getTitulo(),
                h.getDescripcion(),
                h.getPrecio(),
                h.getDireccion(),
                h.getImagen(),
                h.getTipo(),
                h.getCaracteristicaCama(),
                h.getCaracteristicaBano(),
                h.getCaracteristicaTamano(),
                h.getIdPropietario()
        });
    }

    public List<Habitacion> getHabitacionesDisponibles() {
        List<Habitacion> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM habitaciones WHERE id_inquilino IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                Habitacion h = new Habitacion();
                h.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_habitacion")));
                h.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                h.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                h.setPrecio(cursor.getString(cursor.getColumnIndexOrThrow("precio")));
                h.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow("direccion")));
                h.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
                h.setCaracteristicaCama(cursor.getString(cursor.getColumnIndexOrThrow("caracteristica_cama")));
                h.setCaracteristicaBano(cursor.getString(cursor.getColumnIndexOrThrow("caracteristica_bano")));
                h.setCaracteristicaTamano(cursor.getString(cursor.getColumnIndexOrThrow("caracteristica_tamano")));
                h.setImagen(cursor.getBlob(cursor.getColumnIndexOrThrow("imagen")));
                lista.add(h);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }


}
