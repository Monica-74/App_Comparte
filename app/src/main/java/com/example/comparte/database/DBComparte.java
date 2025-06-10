package com.example.comparte.database;

/**
 * Clase DBComparte
 *
 * Esta clase extiende SQLiteOpenHelper y se encarga de la creación, actualización y gestión
 * de la base de datos local SQLite utilizada por la aplicación CompArte.
 *
 * Define la estructura de las tablas (usuarios, inquilinos, propietarios, habitaciones, reservas, etc.),
 * así como los métodos necesarios para realizar operaciones CRUD (crear, leer, actualizar, eliminar).
 *
 * Es la clase responsable de inicializar la base de datos en el dispositivo, mantener su integridad
 * y facilitar el acceso a los datos mediante métodos personalizados.
 *
 * Se utiliza en conjunto con otras clases como DatabaseManager para separar la lógica de negocio
 * de la lógica de persistencia.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.comparte.entities.EstadoReserva;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.entities.Reserva;
import com.example.comparte.entities.Usuario;
import com.example.comparte.utils.CifradoContrasena;

import java.util.ArrayList;
import java.util.List;

public class DBComparte extends SQLiteOpenHelper {// Clase para el rol de inquilino y propietario y administrador de la base de datos de la aplicación

    private static final String DATABASE_NAME = "comparte.db";
    private static final int DATABASE_VERSION = 32;

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
    } // Constructor de la clase DBComparte que recibe el contexto de la aplicación

    @Override
    public void onCreate(SQLiteDatabase db) { // Método que se ejecuta al crear la base de datos y crea las tablas necesarias en la base de datos de la aplicación

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USUARIOS + " (" + // Crea la tabla usuarios en la base de datos
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

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADMIN + " (" + // Crea la tabla admin en la base de datos
                "id_admin INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "email TEXT," +
                "id_inquilino INTEGER ," +
                "id_propietario INTEGER ," +
                "FOREIGN KEY(id_inquilino) REFERENCES inquilinos(id_inquilino)," +
                "FOREIGN KEY(id_propietario) REFERENCES propietarios(id_propietario)" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_INQUILINO + " (" + // Crea la tabla inquilinos en la base de datos
                "id_inquilino INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "edad TEXT," +
                "email TEXT," +
                "telefono TEXT," +
                "sexo TEXT," +
                "id_propietario INTEGER ," +
                "id_habitacion  INTEGER ," +
                "id_usuario INTEGER," +
                "FOREIGN KEY(id_usuario) REFERENCES usuarios(id), " +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones (id_habitacion)" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROPIETARIO + " (" + // Crea la tabla propietarios en la base de datos
                "id_propietario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_apellidos TEXT," +
                "email TEXT," +
                "edad TEXT," +
                "telefono TEXT," +
                "sexo TEXT," +
                "id_inquilino INTEGER ," +
                "id_habitacion  INTEGER ," +
                "id_usuario INTEGER ," +
                "FOREIGN KEY(id_usuario) REFERENCES usuarios(id), " +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones(id_habitacion)" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HABITACIONES + " (" + // Crea la tabla habitaciones en la base de datos
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
                "id_inquilino INTEGER ," +
                "FOREIGN KEY(id_propietario) REFERENCES propietario(id_propietario)," +
                "FOREIGN KEY(id_inquilino) REFERENCES inquilinos(id_inquilino)" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CHAT + " (" + // Crea la tabla chat en la base de datos
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

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RESERVA + "(" + // Crea la tabla reservas en la base de datos
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
                "estado TEXT DEFAULT 'PENDIENTE'," +
                "FOREIGN KEY(id_inquilino) REFERENCES inquilino(id_inquilino)," +
                "FOREIGN KEY(id_habitacion) REFERENCES habitaciones(id_habitacion)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) { // Método que se ejecuta al actualizar la base de datos y elimina las tablas anteriores
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITACIONES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_INQUILINO);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPIETARIO);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVA);

        onCreate(database);
    }


    public Usuario obtenerUsuarioPorEmail(String email) { // Método para obtener un usuario por su email
        SQLiteDatabase database = this.getReadableDatabase(); // Abre la base de datos en modo lectura
        Cursor cursor = database.rawQuery("SELECT * FROM usuarios WHERE email = ?", new String[]{email.toLowerCase()}); // Realiza una consulta para obtener el usuario por su email

        if (cursor != null && cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            Usuario usuario = new Usuario(); // Crea un objeto Usuario
            usuario.setId_usuario(cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario"))); // Obtiene el valor de la columna "id_usuario"
            usuario.setNombreUsuario(cursor.getString(cursor.getColumnIndexOrThrow("nombre_usuario"))); // Obtiene el valor de la columna "nombre_usuario"
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email"))); // Obtiene el valor de la columna "email"
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password"))); // Obtiene el valor de la columna "password"
            usuario.setRol(cursor.getString(cursor.getColumnIndexOrThrow("rol"))); // Obtiene el valor de la columna "rol"


            String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol")); // Obtiene el valor de la columna "rol"
            usuario.setRol(rol != null ? rol.toLowerCase() : ""); // Asigna el valor de "rol" en minúsculas al objeto Usuario

            cursor.close(); // Cierra el cursor
            return usuario; // Devuelve el objeto Usuario
        }

        if (cursor != null) cursor.close(); // Cierra el cursor
        return null; // Devuelve null si no se encontraron resultados
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // Anotación para indicar que se requiere una versión de API específica
    public boolean insertarUsuario(Usuario usuario) { // Método para insertar un nuevo usuario en la base de datos
        SQLiteDatabase db = null; // Instancia de la base de datos
        try { // Intenta insertar el usuario en la base de datos
            db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
            if (db != null) { // Verifica si la base de datos está abierta
                db.beginTransaction(); // Inicia una transacción

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

                long usuarioId = db.insert(TABLE_USUARIOS, null, valuesUsuario); // Inserta el usuario en la tabla usuarios
                if (usuarioId == -1) return false; // Si no se ha podido insertar, devuelve false

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
                    db.insert(TABLE_PROPIETARIO, null, valuesRol); // Inserta el usuario en la tabla propietario
                } else if (rol.equals("inquilino")) {
                    db.insert(TABLE_INQUILINO, null, valuesRol); // Inserta el usuario en la tabla inquilino
                } else if (rol.equals("admin")) {
                    ContentValues valuesAdmin = new ContentValues(); // Inserta el usuario en la tabla admin
                    valuesAdmin.put("nombre_apellidos", usuario.getNombreUsuario()); // Inserta el usuario en la tabla admin
                    valuesAdmin.put("email", usuario.getEmail()); // Inserta el usuario en la tabla admin
                    db.insert(TABLE_ADMIN, null, valuesAdmin); // Inserta el usuario en la tabla admin
                }

                db.setTransactionSuccessful(); // Confirma la transacción
                return true; // Si se ha podido insertar, devuelve true
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


    public int obtenerIdPropietarioPorUsuario(int idUsuario) { // Método para obtener el ID del propietario asociado a un usuario
        SQLiteDatabase db = null; // Instancia de la base de datos
        Cursor cursor = null; // Cursor para recorrer los resultados de la consulta
        int idPropietario = -1; // ID del propietario

        try { // Intenta obtener el ID del propietario asociado a un usuario
            db = this.getReadableDatabase(); // Abre la base de datos en modo lectura
            cursor = db.rawQuery( // Realiza una consulta para obtener el ID del propietario asociado a un usuario
                    "SELECT id_propietario FROM propietario WHERE id_usuario = ?", // Consulta SQL para buscar un usuario por email y contraseña en la tabla "usuarios" de la base de datos
                    new String[]{String.valueOf(idUsuario)} // Parámetros de la consulta
            );

            if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
                idPropietario = cursor.getInt(cursor.getColumnIndexOrThrow("id_propietario")); // Obtiene el valor de la columna "id_propietario"
            }

        } catch (Exception e) { // Manejo de excepciones
            e.printStackTrace(); // para ver el error si ocurre
        } finally { // Cierre del cursor y la base de datos
            if (cursor != null && !cursor.isClosed()) { // Verifica si el cursor no está cerrado
                cursor.close(); // Cierra el cursor
            }
            if (db != null && db.isOpen()) { // Verifica si la base de datos está abierta
                db.close(); // Cierra la base de datos
            }
        }

        return idPropietario; // Devuelve el ID del propietario asociado a un usuario
    }


    public List<Habitacion> getHabitacionesPorPropietario(int idPropietario) { // Método para obtener las habitaciones de un propietario
        List<Habitacion> lista = new ArrayList<>(); // Lista de habitaciones
        SQLiteDatabase db = this.getReadableDatabase(); // Abre la base de datos en modo lectura
        Cursor cursor = db.rawQuery("SELECT * FROM habitaciones WHERE id_propietario = ?", new String[]{String.valueOf(idPropietario)}); // Realiza una consulta para obtener las habitaciones de un propietario

        if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            do { // Recorre los resultados de la consulta
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

                lista.add(h); // Añade la habitación a la lista
            } while (cursor.moveToNext()); // Mueve al siguiente resultado
        }

        cursor.close(); // Cierra el cursor
        return lista; // Devuelve la lista de habitaciones
    }

    public void eliminarHabitacion(int idHabitacion) { // Método para eliminar una habitación
        SQLiteDatabase db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
        db.delete("habitaciones", "id_habitacion = ?", new String[]{String.valueOf(idHabitacion)}); // Elimina la habitación de la base de datos
    }

    public void actualizarHabitacion(Habitacion h) { // Método para actualizar una habitación
        SQLiteDatabase db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
        ContentValues values = new ContentValues(); // Crea un objeto ContentValues para almacenar los valores a actualizar en la base de datos
        values.put("titulo", h.getTitulo()); // Actualiza los valores de la habitación
        values.put("descripcion", h.getDescripcion());  // Actualiza los valores de la habitación
        values.put("precio", h.getPrecio()); // Actualiza los valores de la habitación
        values.put("direccion", h.getDireccion()); // Actualiza los valores de la habitación
        values.put("imagen", h.getImagen()); // Actualiza los valores de la habitación
        values.put("tipo", h.getTipo()); // Actualiza los valores de la habitación
        values.put("caracteristica_cama", h.getCaracteristicaCama()); // Actualiza los valores de la habitación
        values.put("caracteristica_bano", h.getCaracteristicaBano()); // Actualiza los valores de la habitación
        values.put("caracteristica_tamano", h.getCaracteristicaTamano()); // Actualiza los valores de la habitación

        db.update("habitaciones", values, "id_habitacion = ?", new String[]{String.valueOf(h.getId())}); // Actualiza la habitación en la base de datos con los nuevos valores
    }


    public void insertarHabitacion(Habitacion h) { // Método para insertar una nueva habitación en la base de datos
        SQLiteDatabase db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
        ContentValues values = new ContentValues(); // Crea un objeto ContentValues para almacenar los valores a insertar en la base de datos

        values.put("titulo", h.getTitulo());
        values.put("descripcion", h.getDescripcion());
        values.put("precio", h.getPrecio());
        values.put("direccion", h.getDireccion());
        values.put("imagen", h.getImagen());
        values.put("tipo", h.getTipo());
        values.put("caracteristica_cama", h.getCaracteristicaCama());
        values.put("caracteristica_bano", h.getCaracteristicaBano());
        values.put("caracteristica_tamano", h.getCaracteristicaTamano());
        values.put("id_propietario", h.getIdPropietario());
        // No añado id_inquilino, lo dejo fuera  para que se guarde en null automáticamente

        db.insert("habitaciones", null, values); // Inserta la habitación en la base de datos
    }


    public List<Habitacion> getHabitacionesDisponibles() { // Método para obtener las habitaciones disponibles
        List<Habitacion> lista = new ArrayList<>(); // Lista de habitaciones
        SQLiteDatabase db = this.getReadableDatabase(); // Abre la base de datos en modo lectura
        Cursor cursor = db.rawQuery("SELECT * FROM habitaciones WHERE id_inquilino IS NULL", null); // Realiza una consulta para obtener las habitaciones disponibles

        if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            do { // Recorre los resultados de la consulta
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
            } while (cursor.moveToNext()); // Mueve al siguiente resultado
        }

        cursor.close(); // Cierra el cursor
        return lista; // Devuelve la lista de habitaciones
    }

    public String obtenerTelefonoPropietario(int idPropietario) { // Método para obtener el teléfono de un propietario por su ID
        String telefono = "";
        SQLiteDatabase db = this.getReadableDatabase(); // Abre la base de datos en modo lectura
        Cursor cursor = db.rawQuery( // Realiza una consulta para obtener el teléfono de un propietario por su ID
                "SELECT telefono FROM propietario WHERE id_propietario = ?", // Consulta SQL para buscar un usuario por email y contraseña en la tabla "usuarios" de la base de datos
                new String[]{String.valueOf(idPropietario)} // Parámetros de la consulta
        );
        if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            telefono = cursor.getString(0); // Obtiene el valor del teléfono
        }
        cursor.close(); // Cierra el cursor
        return telefono; // Devuelve el teléfono del propietario
    }


    public boolean insertarReserva(Reserva reserva) { // Método para insertar una nueva reserva en la base de datos
        SQLiteDatabase db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
        ContentValues values = new ContentValues(); // Crea un objeto ContentValues para almacenar los valores a insertar en la base de datos

        values.put("nombre_inquilino", reserva.getNombreInquilino());
        values.put("descripcion_habitacion", reserva.getDescripcionHabitacion());
        values.put("fecha_reserva", reserva.getFechaReserva());
        values.put("telefono_inquilino", reserva.getTelefonoInquilino());
        values.put("email_inquilino", reserva.getEmailInquilino());
        values.put("id_inquilino", reserva.getIdInquilino());
        values.put("id_habitacion", reserva.getIdHabitacion());
        values.put("fecha_inicio", reserva.getFechaInicio());
        values.put("fecha_fin", reserva.getFechaFin());
        values.put("estado", reserva.getEstadoString()); // Guarda "PENDIENTE", "CONFIRMADA", etc.

        long resultado = db.insert("reserva", null, values); // Inserta la reserva en la base de datos

        db.close(); // Cierra la base de datos
        return resultado != -1; // Devuelve true si se insertó correctamente
    }

    public boolean actualizarEstadoReserva(int idReserva, int idHabitacion, String nuevoEstado) { // Método para actualizar el estado de una reserva
        SQLiteDatabase db = this.getWritableDatabase(); // Abre la base de datos en modo escritura
        ContentValues values = new ContentValues(); // Crea un objeto ContentValues para almacenar los valores a actualizar en la base de datos
        values.put("estado", nuevoEstado); // Actualiza el estado de la reserva
        int rows = db.update("reserva", values, "id_reserva = ?", new String[]{String.valueOf(idReserva)}); // Actualiza el estado de la reserva en la base de datos
        return rows > 0; // Devuelve true si se actualizó correctamente
    }

    public List<Reserva> getReservasDePropietario(int idPropietario) { // Método para obtener las reservas de un propietario
        List<Reserva> reservas = new ArrayList<>(); // Lista de reservas
        SQLiteDatabase db = this.getReadableDatabase(); // Abre la base de datos en modo lectura

        String query = "SELECT r.* FROM reserva r " +
                "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
                "WHERE h.id_propietario = ?"; // Consulta SQL para obtener las reservas de un propietario por su ID de propietario

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idPropietario)}); // Realiza la consulta a la base de datos

        if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            do {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(cursor.getInt(cursor.getColumnIndexOrThrow("id_reserva")));
                reserva.setIdInquilino(cursor.getInt(cursor.getColumnIndexOrThrow("id_inquilino")));
                reserva.setIdHabitacion(cursor.getInt(cursor.getColumnIndexOrThrow("id_habitacion")));
                reserva.setNombreInquilino(cursor.getString(cursor.getColumnIndexOrThrow("nombre_inquilino")));
                reserva.setDescripcionHabitacion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion_habitacion")));
                reserva.setFechaReserva(cursor.getString(cursor.getColumnIndexOrThrow("fecha_reserva")));
                reserva.setTelefonoInquilino(cursor.getString(cursor.getColumnIndexOrThrow("telefono_inquilino")));
                reserva.setEmailInquilino(cursor.getString(cursor.getColumnIndexOrThrow("email_inquilino")));
                reserva.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio")));
                reserva.setFechaFin(cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin")));

                String estadoStr = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                reserva.setEstado(EstadoReserva.valueOf(estadoStr)); // Enum

                reservas.add(reserva); // Añade la reserva a la lista
            } while (cursor.moveToNext()); // Mueve al siguiente resultado
        }

        cursor.close(); // Cierra el cursor
        db.close(); // Cierra la base de datos
        return reservas; // Devuelve la lista de reservas
    }


    public List<Reserva> obtenerReservasPorPropietario(int idPropietario) { // Método para obtener las reservas de un propietario
        List<Reserva> lista = new ArrayList<>(); // Lista de reservas
        SQLiteDatabase db = this.getReadableDatabase(); // Abre la base de datos en modo lectura

        String query = "SELECT r.id_reserva, r.nombre_inquilino, r.descripcion_habitacion, r.fecha_reserva, " +
                "r.telefono_inquilino, r.email_inquilino, r.id_inquilino, r.id_habitacion, " +
                "r.fecha_inicio, r.fecha_fin, r.estado " +
                "FROM reserva r JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
                "WHERE h.id_propietario = ?"; // Consulta SQL para obtener las reservas de un propietario por su ID de propietario


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idPropietario)}); // Realiza la consulta a la base de datos

        if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            do {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(cursor.getInt(cursor.getColumnIndexOrThrow("id_reserva")));
                reserva.setNombreInquilino(cursor.getString(cursor.getColumnIndexOrThrow("nombre_inquilino")));
                reserva.setDescripcionHabitacion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion_habitacion")));
                reserva.setFechaReserva(cursor.getString(cursor.getColumnIndexOrThrow("fecha_reserva")));
                reserva.setTelefonoInquilino(cursor.getString(cursor.getColumnIndexOrThrow("telefono_inquilino")));
                reserva.setEmailInquilino(cursor.getString(cursor.getColumnIndexOrThrow("email_inquilino")));
                reserva.setIdInquilino(cursor.getInt(cursor.getColumnIndexOrThrow("id_inquilino")));
                reserva.setIdHabitacion(cursor.getInt(cursor.getColumnIndexOrThrow("id_habitacion")));
                reserva.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio")));
                reserva.setFechaFin(cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin")));
                reserva.setEstado(EstadoReserva.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("estado"))));

                lista.add(reserva); // Añade la reserva a la lista
            } while (cursor.moveToNext()); // Mueve al siguiente resultado
        }

        cursor.close(); // Cierra el cursor
        db.close(); // Cierra la base de datos
        return lista; // Devuelve la lista de reservas
    }

    public List<Reserva> obtenerReservasPorInquilino(int idInquilino) { // Método para obtener las reservas de un inquilino
        List<Reserva> reservas = new ArrayList<>(); // Lista de reservas
        SQLiteDatabase db = this.getReadableDatabase(); // Abre la base de datos en modo lectura

        String query = "SELECT * FROM reserva WHERE id_inquilino = ?"; // Consulta SQL para obtener las reservas de un inquilino por su ID de inquilino
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idInquilino)}); // Realiza la consulta a la base de datos

        if (cursor.moveToFirst()) { // Verifica si se encontraron resultados en la consulta
            do { // Recorre los resultados de la consulta
                Reserva reserva = new Reserva();
                reserva.setIdReserva(cursor.getInt(cursor.getColumnIndexOrThrow("id_reserva")));
                reserva.setIdInquilino(cursor.getInt(cursor.getColumnIndexOrThrow("id_inquilino")));
                reserva.setIdHabitacion(cursor.getInt(cursor.getColumnIndexOrThrow("id_habitacion")));
                reserva.setNombreInquilino(cursor.getString(cursor.getColumnIndexOrThrow("nombre_inquilino")));
                reserva.setDescripcionHabitacion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion_habitacion")));
                reserva.setFechaReserva(cursor.getString(cursor.getColumnIndexOrThrow("fecha_reserva")));
                reserva.setTelefonoInquilino(cursor.getString(cursor.getColumnIndexOrThrow("telefono_inquilino")));
                reserva.setEmailInquilino(cursor.getString(cursor.getColumnIndexOrThrow("email_inquilino")));
                reserva.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio")));
                reserva.setFechaFin(cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin")));

                String estadoStr = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                reserva.setEstado(EstadoReserva.valueOf(estadoStr));

                reservas.add(reserva); // Añade la reserva a la lista
            } while (cursor.moveToNext()); // Mueve al siguiente resultado
        }

        cursor.close(); // Cierra el cursor
        db.close(); // Cierra la base de datos
        return reservas; // Devuelve la lista de reservas
    }

}

