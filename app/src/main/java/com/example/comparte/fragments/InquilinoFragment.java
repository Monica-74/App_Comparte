package com.example.comparte.fragments;
/*
 * Clase InquilinoFragment
 *
 * Fragmento principal mostrado al usuario con rol de inquilino tras iniciar sesión en la aplicación CompArte.
 * Actúa como panel de inicio para este tipo de usuario, ofreciendo accesos directos a las principales funcionalidades
 * disponibles para inquilinos.
 *
 * Desde este fragmento, el usuario puede:
 * - Ver habitaciones disponibles para reservar.
 * - Acceder a sus reservas realizadas.
 * - Consultar información personal o cerrar sesión.
 *
 * Este fragmento organiza la experiencia del inquilino dentro de la aplicación, y forma parte del flujo de navegación principal
 * gestionado por el NavController.
 */


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.comparte.R;

public class InquilinoFragment extends Fragment { // Clase InquilinoFragment que hereda de Fragment para mostrar las habitaciones disponibles.

    public InquilinoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_inquilino, container, false); // Inflar el layout del fragmento.

        Button btnBuscar = view.findViewById(R.id.btnBuscarHabitacion); // Enlazar el botón "Buscar Habitación" a la vista.
        btnBuscar.setOnClickListener(v -> { // Configurar el clic del botón.
            NavController navController = Navigation.findNavController(view); // Obtener el controlador de navegación.
            navController.navigate(R.id.habitacionesDisponiblesFragment); // Navegar al fragmento de habitaciones disponibles.
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) // Supresión de advertencia de inflado de vista
        Button btnMisReservas = view.findViewById(R.id.btnMisReservas); // Enlazar el botón "Mis Reservas" a la vista.

        btnMisReservas.setOnClickListener(v -> { // Configurar el clic del botón.
            Navigation.findNavController(view).navigate(R.id.action_inquilinoFragment_to_misReservasFragment); // Navegar al fragmento de mis reservas.
        });

        return view; // Devolver la vista del fragmento.
    }
}
