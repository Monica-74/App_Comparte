package com.example.comparte.fragments;

/*
 * Clase HabitacionFragment
 *
 * Fragmento genérico utilizado para mostrar una lista de habitaciones dentro de la aplicación CompArte.
 * Su comportamiento puede variar según el contexto o el rol del usuario, mostrándose como parte de una vista general,
 * de gestión o de consulta de habitaciones.
 *
 * Utiliza un RecyclerView para presentar los anuncios de forma estructurada, permitiendo la interacción con cada ítem
 * para ver más detalles, editarlos o eliminarlos, dependiendo del rol del usuario y del flujo de navegación.
 *
 * Este fragmento puede estar conectado a una base de datos local (SQLite) para cargar dinámicamente los datos,
 * y actúa como puente entre la interfaz visual y la lógica de almacenamiento de habitaciones.
 */


import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.utils.SessionManager;

import java.util.List;

public class HabitacionFragment extends Fragment { //  Clase HabitacionFragment que hereda de Fragment para mostrar las habitaciones.

    private RecyclerView recyclerHabitaciones;
    private View layoutSinAnuncios;
    private DBComparte dbComparte;
    private SessionManager sessionManager;
    private HabitacionAdapter adapter;

    public HabitacionFragment() {}

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        // Inflar layout que contiene el RecyclerView y el layout vacío
        View view = inflater.inflate(R.layout.fragment_habitacion, container, false);

        recyclerHabitaciones = view.findViewById(R.id.recyclerHabitaciones); // Enlazar el RecyclerView a la vista.
        layoutSinAnuncios = view.findViewById(R.id.layoutSinAnuncios); // Enlazar el layout vacío a la vista.
        recyclerHabitaciones.setLayoutManager(new LinearLayoutManager(getContext())); // Configurar el LinearLayoutManager para el RecyclerView.



        dbComparte = new DBComparte(getContext());  // Crear una instancia de DBComparte para interactuar con la base de datos.
        sessionManager = new SessionManager(getContext()); // Crear una instancia de SessionManager para gestionar la sesión del usuario.

        int idPropietario = sessionManager.getPropietarioId(); // Obtener el ID del propietario actual de la sesión.
        List<Habitacion> habitaciones = dbComparte.getHabitacionesPorPropietario(idPropietario); // Obtener la lista de habitaciones del propietario desde la base de datos.

        // Verificar si hay habitaciones
        if (habitaciones.isEmpty()) {
            layoutSinAnuncios.setVisibility(View.VISIBLE);
            recyclerHabitaciones.setVisibility(View.GONE);
        } else {
            layoutSinAnuncios.setVisibility(View.GONE);
            recyclerHabitaciones.setVisibility(View.VISIBLE);

            adapter = new HabitacionAdapter(getContext(), habitaciones, new HabitacionAdapter.OnHabitacionActionListener() { // Crear un adaptador personalizado para el RecyclerView.
                @Override
                public void modificarHabitacion(Habitacion habitacion) { // Método para manejar la acción de modificar una habitación.
                    Bundle bundle = new Bundle(); // Crear un objeto Bundle para pasar datos entre fragmentos.
                    bundle.putSerializable("habitacion", habitacion); // tu clase Habitacion debe implementar Serializable
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main); // Obtener el controlador de navegación.
                    navController.navigate(R.id.formularioFragment, bundle); // Navegar al fragmento de formulario para modificar la habitación.
                }

                @Override
                public void eliminarHabitacion(Habitacion habitacion) { // Método para manejar la acción de eliminar una habitación.
                    new AlertDialog.Builder(getContext()) // Mostrar un diálogo de confirmación de eliminación.
                            .setTitle("Confirmar eliminación") // Configurar el título del diálogo.
                            .setMessage("¿Estás segur@ de que quieres eliminar este anuncio?") // Configurar el mensaje del diálogo.
                            .setPositiveButton("Eliminar", (dialog, which) -> { // Configurar el botón "Eliminar".
                                dbComparte.eliminarHabitacion(habitacion.getId()); // Eliminar la habitación de la base de datos.
                                habitaciones.remove(habitacion); // Modifica la habitación de la lista.
                                adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado.
                                Toast.makeText(getContext(), "Anuncio eliminado: " + habitacion.getTitulo(), Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de éxito.

                                if (habitaciones.isEmpty()) { // Verificar si la lista de habitaciones está vacía.
                                    layoutSinAnuncios.setVisibility(View.VISIBLE); // Mostrar el layout vacío.
                                    recyclerHabitaciones.setVisibility(View.GONE); // Ocultar el RecyclerView.
                                }
                            })
                            .setNegativeButton("Cancelar", null) // Configurar el botón "Cancelar".
                            .show(); // Mostrar el diálogo.
                }
            }, false); // Indicar que el adaptador no está en modo de edición.

            recyclerHabitaciones.setAdapter(adapter); // Asignar el adaptador al RecyclerView.
        }

        // Acción del botón "Crear anuncio" en layout vacío
        View btnCrear = view.findViewById(R.id.btnCrearAnuncio); // Enlazar el botón "Crear anuncio" a la vista.
        if (btnCrear != null) { // Verificar si el botón existe en la vista
            btnCrear.setOnClickListener(v -> { // Configurar el clic del botón.
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main); // Obtener el controlador de navegación.
                navController.navigate(R.id.formularioFragment); // Navegar al fragmento de formulario para crear una nueva habitación.
            });
        }

        return view; // Devolver la vista del fragmento.
    }
}
