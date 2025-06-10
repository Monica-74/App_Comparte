package com.example.comparte.entities;
/*
 * Clase Habitacion
 *
 * Representa una habitación publicada en la aplicación CompArte por parte de un propietario.
 * Esta clase actúa como modelo de datos y contiene toda la información relevante de una habitación,
 * incluyendo título, descripción, dirección, precio, tipo, características, imagen, y el ID del propietario.
 *
 * Es utilizada para crear, mostrar, modificar o eliminar anuncios de habitaciones,
 * tanto en la interfaz de usuario como en la base de datos.
 *
 * Esta clase puede ser serializada para facilitar su paso entre fragmentos o actividades
 * y es clave en operaciones como reservas, listados y filtrados.
 */


import java.io.Serializable;

public class Habitacion implements Serializable {
    private int id;
    private String titulo;
    private String descripcion;
    private String precio;
    private String direccion;
    private String caracteristicaCama;
    private String caracteristicaBano;
    private String caracteristicaTamano;

    private String tipo; // tipo de vivienda o habitación femenino o masculino
    private byte[] imagen;
    private int idPropietario;
    private String telefonoContacto;

    public Habitacion(String descripcion, int id, String titulo, String precio, String direccion, String caracteristicaCama, String caracteristicaBano, String caracteristicaTamano, String tipo, byte[] imagen, int idPropietario) {
        this.descripcion = descripcion;
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.direccion = direccion;
        this.caracteristicaCama = caracteristicaCama;
        this.caracteristicaBano = caracteristicaBano;
        this.caracteristicaTamano = caracteristicaTamano;
        this.tipo = tipo;
        this.imagen = imagen;
        this.idPropietario = idPropietario;
    }



    public Habitacion() {
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getCaracteristicaCama() {
        return caracteristicaCama;
    }

    public String getCaracteristicaBano() {

        return "";
    }

    public String getCaracteristicaTamano() {

         return  caracteristicaTamano;
    }

    public byte [] getImage() {
        return imagen;
    }

    public void setCaracteristicaCama(String cama) {
        this.caracteristicaCama = cama;
    }

    public void setCaracteristicaBano(String bano) {
        this.caracteristicaBano = bano;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public void setCaracteristicaTamano(String tamano) {
        this.caracteristicaTamano = tamano;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }
}
