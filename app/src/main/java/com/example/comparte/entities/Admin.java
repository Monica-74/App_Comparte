package com.example.comparte.entities;

public class Admin {
    private int id_admin ;
    private String nombre_apellidos;
    private String email = "admin@comparte.com";
    private String password = "admin";

    public Admin(int id_admin, String nombre_apellidos, String email, String password) {
        this.id_admin = id_admin;
        this.nombre_apellidos = nombre_apellidos;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin() {
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getNombre_apellidos() {
        return nombre_apellidos;
    }

    public void setNombre_apellidos(String nombre_apellidos) {
        this.nombre_apellidos = nombre_apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
