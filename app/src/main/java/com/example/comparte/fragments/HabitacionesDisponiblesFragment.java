package com.example.comparte.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;

import java.util.List;
/*
 * Clase HabitacionesDisponiblesFragment
 *
 * Fragmento destinado a mostrar al inquilino una lista de habitaciones disponibles publicadas por propietarios.
 * Utiliza un RecyclerView para presentar los anuncios de forma dinámica y visual, permitiendo a los usuarios
 * explorar diferentes opciones de alojamiento.
 *
 * Cada habitación listada incluye información básica como imagen, título, dirección, precio y tipo de habitación.
 * Al seleccionar una habitación, se navega al fragmento de detalle correspondiente (DetalleFragment),
 * donde se muestra la información completa y se puede iniciar el proceso de reserva.
 *
 * Este fragmento actúa como punto de entrada para los inquilinos en la búsqueda de alojamiento dentro de la plataforma.
 */


public class HabitacionesDisponiblesFragment extends Fragment { // Clase HabitacionesDisponiblesFragment que hereda de Fragment para mostrar las habitaciones disponibles.

    private RecyclerView recyclerView;

    public HabitacionesDisponiblesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_habitaciones_disponibles, container, false); // Inflar el layout del fragmento.

        recyclerView = view.findViewById(R.id.recyclerViewHabitaciones); // Enlazar el RecyclerView a la vista.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Configurar el LinearLayoutManager para el RecyclerView.

        recyclerView.post(() -> recyclerView.scrollToPosition(0)); // Ajustar la posición inicial del RecyclerView.


        DBComparte db = new DBComparte(getContext()); // Crear una instancia de DBComparte para interactuar con la base de datos.
        List<Habitacion> habitaciones = db.getHabitacionesDisponibles(); // Obtener la lista de habitaciones disponibles desde la base de datos.

        HabitacionAdapter adapter = new HabitacionAdapter(getContext(), habitaciones, null, false); // Crear un adaptador personalizado para el RecyclerView.
        recyclerView.setAdapter(adapter); // Asignar el adaptador al RecyclerView.

        return view; // Devolver la vista del fragmento.
    }
}
