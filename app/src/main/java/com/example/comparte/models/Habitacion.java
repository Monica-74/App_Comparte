package com.example.comparte.models;
/*
atributos de la vivienda o habitación compartida
 */
public class Habitacion {
    private int id;
    private String titulo;
    private String descripcion;
    private CharSequence precio;
    private String direccion;
    private String caracteristicaCama;
    private String caracteristicaBano;
    private String caracteristicaTamano;

    private String tipo; // tipo de vivienda o habitación femenino o masculino
    private byte[] imagen;

    public Habitacion(int id, String descripcion, String titulo, CharSequence precio, String direccion, String caracteristicaCama, String caracteristicaBano, String caracteristicaTamano, String tipo, byte[] imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.precio = precio;
        this.direccion = direccion;
        this.caracteristicaCama = caracteristicaCama;
        this.caracteristicaBano = caracteristicaBano;
        this.caracteristicaTamano = caracteristicaTamano;
        this.tipo = tipo;
        this.imagen = imagen;
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

    public CharSequence getPrecio() {
        return precio;
    }

    public void setPrecio(CharSequence precio) {
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
        return " ";
    }

    public byte [] getImage() {
        return imagen;
    }
}
