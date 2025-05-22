package com.example.comparte.models;

public class Usuario {
    private int id_usuario;
    private String nombreUsuario;

    private String password;
    private int telefono;
    private String genero;
    private String email;
    private int edad;
    private String rol; // Puede ser "admin", "inquilino" o "propietario"

    public Usuario() {
    }

    public Usuario(int id_usuario, String nombreUsuario, String password, int telefono, String genero, String email, int edad, String rol) {
        this.id_usuario = id_usuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
        this.edad = edad;
        this.rol = rol;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}

