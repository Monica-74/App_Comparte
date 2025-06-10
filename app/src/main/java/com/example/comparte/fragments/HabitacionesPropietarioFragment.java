package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.adapters.ReservaAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.utils.SessionManager;

import java.util.List;
/*
 * Clase HabitacionesPropietarioFragment
 *
 * Fragmento diseñado para que los propietarios visualicen y gestionen las habitaciones que han publicado en la aplicación CompArte.
 * Muestra un listado de anuncios creados por el propietario actual, utilizando un RecyclerView con tarjetas que resumen
 * cada habitación (imagen, título, dirección, precio, etc.).
 *
 * Desde este fragmento, el propietario puede:
 * - Ver los detalles de cada habitación.
 * - Acceder a opciones para modificar o eliminar anuncios existentes.
 * - Crear nuevas habitaciones mediante un botón de acción que abre el FormularioFragment.
 *
 * Este fragmento facilita el control y mantenimiento de los anuncios por parte del propietario,
 * centralizando su gestión en una única interfaz.
 */

public class HabitacionesPropietarioFragment extends Fragment { // Clase HabitacionesPropietarioFragment que hereda de Fragment para mostrar las habitaciones del propietario.

    private RecyclerView recyclerView;
    private DBComparte db;
    private SessionManager sessionManager;

    public HabitacionesPropietarioFragment() {}

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_habitaciones_propietario, container, false); // Inflar el layout del fragmento.

        recyclerView = view.findViewById(R.id.recyclerViewPropietario); // Enlazar el RecyclerView a la vista.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Configurar el LinearLayoutManager para el RecyclerView.

        db = new DBComparte(getContext()); // Crear una instancia de DBComparte para interactuar con la base de datos.
        sessionManager = new SessionManager(getContext()); //  Crear una instancia de SessionManager para gestionar la sesión del usuario.

        int idPropietario = sessionManager.getPropietarioId(); // Obtener el ID del propietario actual de la sesión.
        List<Habitacion> habitaciones = db.getHabitacionesPorPropietario(idPropietario); // Obtener la lista de habitaciones del propietario desde la base de datos.

        HabitacionAdapter adapter = new HabitacionAdapter(getContext(), habitaciones, new HabitacionAdapter.OnHabitacionActionListener() { // Crear un adaptador personalizado para el RecyclerView.
            @Override
            public void modificarHabitacion(Habitacion habitacion) { // Método para manejar la acción de modificar una habitación.
                Bundle bundle = new Bundle(); // Crear un objeto Bundle para pasar datos entre fragmentos.
                bundle.putSerializable("habitacion", habitacion); // tu clase Habitacion debe implementar Serializable

                Navigation.findNavController(requireView()) // Navegar al fragmento de formulario para modificar la habitación.
                        .navigate(R.id.action_propietarioFragment_to_formularioFragment, bundle); // Navegar al fragmento de formulario para modificar la habitación.

            }

            @Override
            public void eliminarHabitacion(Habitacion habitacion) { // Método para manejar la acción de eliminar una habitación.
                NavController navController = Navigation.findNavController(requireView()); // Obtener el controlador de navegación.
                navController.navigate(R.id.action_habitacionesPropietarioFragment_to_propietarioFragment); // Navegar al fragmento de inicio del propietario.
            }
        }, true); // Indicar que el adaptador está en modo de edición.

        recyclerView.setAdapter(adapter); // Asignar el adaptador al RecyclerView.
        return view; // Devolver la vista del fragmento.
    }
}
