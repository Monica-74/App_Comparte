package com.example.comparte.entities;
/*
 * Clase Registro
 *
 * Esta clase gestiona el proceso de registro de nuevos usuarios en la aplicación CompArte.
 * Se encarga de recoger los datos introducidos por el usuario (nombre, email, contraseña, rol, etc.),
 * validarlos y almacenarlos en la base de datos local mediante una inserción segura.
 *
 * También puede incluir lógica para comprobar si el usuario ya existe, aplicar cifrado a la contraseña,
 * y redirigir a la pantalla de login una vez registrado correctamente.
 *
 * Su finalidad es garantizar un alta correcta del usuario y preparar su acceso personalizado según el rol (inquilino, propietario o administrador).
 */

public class Registro {
    private String nombre;
    private String correo;
    private String contrasena;
    private String edad;
    private String genero;
    private int telefono;


    public Registro() {
    }

    public Registro(String nombre, String correo, String contrasena, String edad, String genero, int telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.edad = edad;
        this.genero = genero;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
