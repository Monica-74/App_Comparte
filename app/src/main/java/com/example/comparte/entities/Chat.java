package com.example.comparte.entities;

import java.text.DateFormat;

public class Chat {
    private int id_chat;
    private String mensaje;
    private DateFormat fecha;

    public Chat(int id, String mensaje, DateFormat fecha) {
        this.id_chat = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public Chat() {
    }

    public int getId_chat() {
        return id_chat;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public DateFormat getFecha() {
        return fecha;
    }

    public void setFecha(DateFormat fecha) {
        this.fecha = fecha;
    }
}
