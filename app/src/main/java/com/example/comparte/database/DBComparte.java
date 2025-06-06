package com.example.comparte.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.comparte.entities.Habitacion;
import com.example.comparte.entities.Reserva;
import com.example.comparte.entities.Usuario;
import com.example.comparte.utils.CifradoContrasena;

import java.util.ArrayList;
import java.util.List;

public class DBComparte extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "comparte.db";
    private static final int DATABASE_VERSION = 24;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_HABITACIONES = "habitaciones";
    public static final String TABLE_CHAT = "chat";
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_INQUILINO = "inquilino";
    public static final String TABLE_PROPIETARIO = "propietario";
    public static final String TABLE_RESERVA = "reserva";
    private Reserva reserva;

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
                "edad TEXT," +
                "contrasena_hash TEXT" +
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
                "id_usuario INTEGER," +
                "FOREIGN KEY(id_usuario) REFERENCES usuarios(id), " +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones (id_habitacion)" +
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
                "FOREIGN KEY(id_usuario) REFERENCES usuarios(id), " +
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
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "estado TEXT DEFAULT 'pendiente'," +
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (db != null) {
                db.beginTransaction();

                // 1. Generar hashear contraseña
                String hashedPassword = CifradoContrasena.hashPassword(usuario.getPassword());

                // 2. Insertar en la tabla usuarios
                ContentValues valuesUsuario = new ContentValues();
                valuesUsuario.put("nombre_usuario", usuario.getNombreUsuario());
                valuesUsuario.put("password", hashedPassword);  // contraseña cifrada
                valuesUsuario.put("rol", usuario.getRol());
                valuesUsuario.put("telefono", usuario.getTelefono());
                valuesUsuario.put("genero", usuario.getGenero());
                valuesUsuario.put("email", usuario.getEmail());
                valuesUsuario.put("edad", usuario.getEdad());

                long usuarioId = db.insert(TABLE_USUARIOS, null, valuesUsuario);
                if (usuarioId == -1) return false;

                // 3. Insertar en tabla según rol
                String rol = usuario.getRol().toLowerCase();
                ContentValues valuesRol = new ContentValues();
                valuesRol.put("nombre_apellidos", usuario.getNombreUsuario());
                valuesRol.put("email", usuario.getEmail());
                valuesRol.put("edad", usuario.getEdad());
                valuesRol.put("telefono", usuario.getTelefono());
                valuesRol.put("sexo", usuario.getGenero());
                valuesRol.put("id_usuario", (int) usuarioId);

                if (rol.equals("propietario")) {
                    db.insert(TABLE_PROPIETARIO, null, valuesRol);
                } else if (rol.equals("inquilino")) {
                    db.insert(TABLE_INQUILINO, null, valuesRol);
                } else if (rol.equals("admin")) {
                    ContentValues valuesAdmin = new ContentValues();
                    valuesAdmin.put("nombre_apellidos", usuario.getNombreUsuario());
                    valuesAdmin.put("email", usuario.getEmail());
                    db.insert(TABLE_ADMIN, null, valuesAdmin);
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

                String telefono = obtenerTelefonoPropietario(idPropietario);
                h.setTelefonoContacto(telefono);

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
        ContentValues values = new ContentValues();

        values.put("titulo", h.getTitulo());
        values.put("descripcion", h.getDescripcion());
        values.put("precio", h.getPrecio());
        values.put("direccion", h.getDireccion());
        values.put("imagen", h.getImagen()); // asegúrate de que no sea null o maneja el caso
        values.put("tipo", h.getTipo());
        values.put("caracteristica_cama", h.getCaracteristicaCama());
        values.put("caracteristica_bano", h.getCaracteristicaBano());
        values.put("caracteristica_tamano", h.getCaracteristicaTamano());
        values.put("id_propietario", h.getIdPropietario());
        // No añado id_inquilino, lo dejo fuera  para que se guarde en null automáticamente

        db.insert("habitaciones", null, values);
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
    public String obtenerTelefonoPropietario(int idPropietario) {
        String telefono = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT telefono FROM propietario WHERE id_propietario = ?",
                new String[]{String.valueOf(idPropietario)}
        );
        if (cursor.moveToFirst()) {
            telefono = cursor.getString(0);
        }
        cursor.close();
        //db.close();
        return telefono;
    }


    public boolean insertarReserva(Reserva reserva) {
        SQLiteDatabase db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
        ContentValues values = new ContentValues();

        values.put("nombre_inquilino", reserva.getNombreInquilino());
        values.put("descripcion_habitacion", reserva.getDescripcionHabitacion());
        values.put("fecha_reserva", reserva.getFechaReserva());
        values.put("telefono_inquilino", reserva.getTelefonoInquilino());
        values.put("email_inquilino", reserva.getEmailInquilino());
        values.put("id_inquilino", reserva.getIdInquilino());
        values.put("id_habitacion", reserva.getIdHabitacion());
        values.put("fecha_inicio", reserva.getFechaInicio());
        values.put("fecha_fin", reserva.getFechaFin());
        values.put("estado", reserva.getEstado()); // Guarda "PENDIENTE", "CONFIRMADA", etc.

        long resultado= db.insert("reserva", null, values);



        db.close();
        return resultado != -1; // Devuelve true si se insertó correctamente


    }
    public boolean actualizarEstadoReserva(int idReserva, int idHabitacion, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", nuevoEstado);
        int rows = db.update("reserva", values, "id = ?", new String[]{String.valueOf(idReserva)});
        return rows > 0;
    }

}
