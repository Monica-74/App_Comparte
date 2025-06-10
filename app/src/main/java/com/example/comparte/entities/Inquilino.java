package com.example.comparte.entities;

/*
 * Clase Inquilino
 *
 * Representa a un usuario con rol de inquilino dentro de la aplicación CompArte.
 * Esta clase forma parte del modelo de datos y puede contener atributos específicos
 * de los inquilinos, como su identificador, información personal o preferencias de búsqueda.
 *
 * Su función principal es identificar y diferenciar a los usuarios que buscan alojamiento,
 * permitiendo la gestión de funcionalidades asociadas como realizar reservas,
 * ver habitaciones disponibles o acceder a sus solicitudes.
 *
 * Se utiliza en combinación con la base de datos local para almacenar y recuperar
 * información específica del inquilino.
 */

public class Inquilino {
    private int id_inquilino;
    private int id_usuario;
    private int id_habitacion;
    private int id_propietario;

    public Inquilino(int id_inquilino, int id_usuario, int id_habitacion, int id_propietario) {
        this.id_inquilino = id_inquilino;
        this.id_usuario = id_usuario;
        this.id_habitacion = id_habitacion;
        this.id_propietario = id_propietario;
    }

    public int getId_inquilino() {
        return id_inquilino;
    }

    public void setId_inquilino(int id_inquilino) {
        this.id_inquilino = id_inquilino;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public int getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(int id_propietario) {
        this.id_propietario = id_propietario;
    }

    public Inquilino() {
    }

}
