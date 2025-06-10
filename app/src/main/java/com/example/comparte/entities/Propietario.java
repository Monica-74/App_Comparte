package com.example.comparte.entities;
/*
 * Clase Propietario
 *
 * Representa a un usuario con rol de propietario dentro de la aplicación CompArte.
 * Esta clase forma parte del modelo de datos y encapsula la información específica de los propietarios,
 * como su identificador u otros posibles datos asociados (nombre, contacto, etc.).
 *
 * Su principal función es permitir la gestión de anuncios de habitaciones,
 * así como el control de reservas recibidas y la interacción con los inquilinos interesados.
 *
 * Se utiliza junto a la base de datos local para guardar y acceder a los datos relacionados
 * con cada propietario registrado en la plataforma.
 */


public class Propietario {

        private int id_propietario;
        private int id_usuario;
        private int id_habitacion;
        private int id_inquilino;

    public Propietario() {
    }

    public Propietario(int id_propietario, int id_usuario, int id_habitacion, int id_inquilino) {
        this.id_propietario = id_propietario;
        this.id_usuario = id_usuario;
        this.id_habitacion = id_habitacion;
        this.id_inquilino = id_inquilino;
    }

    public int getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(int id_propietario) {
        this.id_propietario = id_propietario;
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

    public int getId_inquilino() {
        return id_inquilino;
    }

    public void setId_inquilino(int id_inquilino) {
        this.id_inquilino = id_inquilino;
    }
}


