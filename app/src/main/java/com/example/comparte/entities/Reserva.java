package com.example.comparte.entities;

import java.io.Serializable;

public class Reserva implements Serializable {
    private int idReserva;
    private int idHabitacion;
    private int idInquilino;
    private String fechaInicio;
    private String fechaFin;
    private EstadoReserva estado;  // Enum para estado de la reserva
    private String nombreInquilino;
    private String descripcionHabitacion;
    private String fechaReserva;
    private String telefonoInquilino;
    private String emailInquilino;

    public String getNombreInquilino() {
        return nombreInquilino;
    }

    public void setNombreInquilino(String nombreInquilino) {
        this.nombreInquilino = nombreInquilino;
    }

    public String getDescripcionHabitacion() {
        return descripcionHabitacion;
    }

    public void setDescripcionHabitacion(String descripcionHabitacion) {
        this.descripcionHabitacion = descripcionHabitacion;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getTelefonoInquilino() {
        return telefonoInquilino;
    }

    public void setTelefonoInquilino(String telefonoInquilino) {
        this.telefonoInquilino = telefonoInquilino;
    }

    public String getEmailInquilino() {
        return emailInquilino;
    }

    public void setEmailInquilino(String emailInquilino) {
        this.emailInquilino = emailInquilino;
    }

    // Constructor vacío
    public Reserva() {
    }

    // Getters y Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }


    public EstadoReserva getEstado() {
        return estado;
    }

    public String getEstadoString() {
        return estado != null ? estado.name() : null;
    }
}



