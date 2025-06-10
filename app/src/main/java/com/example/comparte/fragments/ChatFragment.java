package com.example.comparte.fragments;

/*
 * Clase ChatFragment: se tiene porques es necesaria para otras clases pero por si misma no tiene funcionalidad propia de interacción con el usuario.
 *
 * Fragmento responsable de gestionar la funcionalidad de mensajería entre usuarios dentro de la aplicación CompArte.
 * Permite la visualización e intercambio de mensajes en tiempo real (o simulada en local), facilitando la comunicación
 * entre inquilinos y propietarios en el contexto de reservas o dudas sobre habitaciones.
 *
 * Este fragmento puede incluir:
 * - Un RecyclerView para mostrar el historial de mensajes.
 * - Un campo de texto para escribir nuevos mensajes.
 * - Botones para enviar mensajes o adjuntar contenido.
 *
 * La lógica del fragmento puede estar conectada con una base de datos local (SQLite) o futura integración con un servidor,
 * y está orientada a ofrecer una experiencia de conversación clara, simple y efectiva.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.comparte.R;

public class ChatFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}
