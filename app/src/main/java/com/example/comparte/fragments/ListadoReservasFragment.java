package com.example.comparte.fragments;

/*
 * Clase ListadoReservasFragment
 *
 * Fragmento destinado a mostrar al propietario la lista de reservas que ha recibido en sus habitaciones publicadas.
 * Utiliza un RecyclerView para presentar cada reserva de forma resumida, permitiendo visualizar datos como:
 * - Nombre del inquilino
 * - Fechas de la reserva
 * - Estado actual (pendiente, confirmada, rechazada)
 *
 * Al pulsar sobre una reserva, se puede navegar a un fragmento detallado (ReservaPropietarioFragment)
 * donde el propietario podrá confirmar o rechazar la solicitud.
 *
 * Este fragmento permite al propietario tener un control organizado y claro de las solicitudes de alojamiento
 * que ha recibido a través de la plataforma.
 */


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.ReservaAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Reserva;
import com.example.comparte.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ListadoReservasFragment extends Fragment { // Clase ListadoReservasFragment que hereda de Fragment para mostrar las habitaciones disponibles.

    private RecyclerView recyclerView;
    private DBComparte db;
    private SessionManager sessionManager;
    private ReservaAdapter adapter;
    private List<Reserva> listaReservas;
    private NavController navController;

    public ListadoReservasFragment() {} // Constructor vacío.

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_listado_reservas, container, false); // Inflar el layout del fragmento.
        navController = NavHostFragment.findNavController(this); // Obtener el controlador de navegación.

        recyclerView = view.findViewById(R.id.recyclerReservas); // Enlazar el RecyclerView a la vista.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Configurar el LinearLayoutManager para el RecyclerView.

        db = new DBComparte(requireContext()); // Crear una instancia de DBComparte para interactuar con la base de datos.
        sessionManager = new SessionManager(requireContext()); // Crear una instancia de SessionManager para gestionar la sesión del usuario.

        listaReservas = new ArrayList<>(); // Crear una lista de reservas vacía.

        adapter = new ReservaAdapter(requireContext(), listaReservas, db, reserva -> { // Crear un adaptador personalizado para el RecyclerView.
            Bundle bundle = new Bundle(); // Crear un objeto Bundle para pasar datos entre fragmentos.
            bundle.putSerializable("reserva", reserva); // tu clase Habitacion debe implementar Serializable
            navController.navigate(R.id.action_listadoReservasFragment_to_reservaPropietarioFragment, bundle); // Navegar al fragmento de formulario para modificar la habitación.
        });

        recyclerView.setAdapter(adapter); // Asignar el adaptador al RecyclerView.

        return view;
    }

    @Override
    public void onResume() { // Método que se ejecuta al volver a la vista del fragmento.
        super.onResume(); // Llamar al método de la clase padre.
        int idPropietario = sessionManager.getPropietarioId(); // Obtener el ID del propietario actual de la sesión.
        listaReservas.clear(); // Limpiar la lista de reservas.
        listaReservas.addAll(db.obtenerReservasPorPropietario(idPropietario)); // Obtener la lista de reservas del propietario desde la base de datos.
        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado.
    }
}
