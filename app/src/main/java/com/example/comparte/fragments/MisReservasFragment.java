package com.example.comparte.fragments;

/*
 * Clase MisReservasFragment
 *
 * Fragmento diseñado para que los inquilinos puedan consultar todas las reservas que han realizado dentro de la aplicación CompArte.
 * Utiliza un RecyclerView para mostrar un listado de sus reservas, incluyendo información relevante como:
 * - Habitación reservada
 * - Fechas de inicio y fin
 * - Estado de la reserva (pendiente, confirmada, rechazada)
 * - Datos de contacto del propietario (si están disponibles)
 *
 * Este fragmento proporciona una vista centralizada para el seguimiento de las reservas activas o anteriores
 * por parte del inquilino, permitiéndole tener un registro claro de su actividad dentro de la plataforma.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.MisReservasAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Reserva;
import com.example.comparte.utils.SessionManager;

import java.util.List;

public class MisReservasFragment extends Fragment { // Clase MisReservasFragment que hereda de Fragment para mostrar las habitaciones disponibles.

    private RecyclerView recyclerView;
    private MisReservasAdapter adapter;
    private DBComparte db;
    private SessionManager sessionManager;

    public MisReservasFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_mis_reservas, container, false); // Inflar el layout del fragmento.

        recyclerView = view.findViewById(R.id.recyclerMisReservas); // Enlazar el RecyclerView a la vista.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));   // Configurar el LinearLayoutManager para el RecyclerView.

        db = new DBComparte(requireContext()); // Crear una instancia de DBComparte para interactuar con la base de datos.
        sessionManager = new SessionManager(requireContext()); // Crear una instancia de SessionManager para gestionar la sesión del usuario.

        int idInquilino = sessionManager.getInquilinoId(); // Obtener el ID del inquilino actual de la sesión.
        List<Reserva> reservas = db.obtenerReservasPorInquilino(idInquilino); // Obtener la lista de reservas del inquilino desde la base de datos.

        if (reservas.isEmpty()) { // Verificar si la lista de reservas está vacía.
            Toast.makeText(getContext(), "No tienes reservas registradas", Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de error.
        }

        adapter = new MisReservasAdapter(requireContext(), reservas); // Crear un adaptador personalizado para el RecyclerView.
        recyclerView.setAdapter(adapter); // Asignar el adaptador al RecyclerView.

        return view;
    }
}
