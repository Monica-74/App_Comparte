package com.example.comparte.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.comparte.R;
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


public class PropietarioFragment extends Fragment { // Clase PropietarioFragment que hereda de Fragment para mostrar las habitaciones disponibles.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.
        View view; // Declarar la vista del fragmento.
        try { // Intentar inflar el layout del fragmento.
            view = inflater.inflate(R.layout.fragment_propietario, container, false); // Inflar el layout del fragmento.

            Button btnPublicidad = view.findViewById(R.id.btnPublicidad); // Enlazar el botón "Publicidad" a la vista.
            btnPublicidad.setOnClickListener(v -> // Configurar el clic del botón.
                    Navigation.findNavController(view).navigate(R.id.action_propietarioFragment_to_formularioFragment) // Navegar al fragmento de formulario.
            );
            Button btnVerAnuncios = view.findViewById(R.id.btnVerAnuncio); // Enlazar el botón "Ver Anuncios" a la vista.
            btnVerAnuncios.setOnClickListener(v -> { // Configurar el clic del botón.
                Navigation.findNavController(view).navigate(R.id.habitacionFragment); // Navegar al fragmento de habitaciones.

            });

            Button btnReserva = view.findViewById(R.id.btnAceptar); // Enlazar el botón "Aceptar" a la vista.
            btnReserva.setOnClickListener(v -> { //
                Navigation.findNavController(v).navigate(R.id.listadoReservasFragment); // Navegar al fragmento de listado de reservas.
            });


            return view;
        } catch (Exception e) { // Capturar cualquier excepción que pueda ocurrir durante la creación de la vista.
            Log.e("PropietarioFragment", "Error al inflar layout", e); // Registrar el error en el registro.
            Toast.makeText(getContext(), "Error cargando la vista: " + e.getMessage(), Toast.LENGTH_LONG).show(); // Mostrar un mensaje de error.
            return null;
        }
    }
}
