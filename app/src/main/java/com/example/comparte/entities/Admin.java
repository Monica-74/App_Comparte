package com.example.comparte.entities;
/**
 * Clase Admin
 *
 * Representa a un usuario con rol de administrador dentro de la aplicación CompArte.
 * Esta clase actúa como entidad del modelo de datos, encapsulando la información
 * relevante de un administrador, como su identificador y posibles atributos adicionales.
 *
 * Aunque puede parecer simple, esta clase permite organizar el código de forma estructurada
 * y facilita la implementación de funcionalidades específicas para este tipo de usuario,
 * como la gestión de usuarios, validaciones o mantenimiento general de la plataforma.
 *
 * Puede ser utilizada junto con la base de datos local para almacenar o recuperar información
 * relacionada con administradores registrados.
 */


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
