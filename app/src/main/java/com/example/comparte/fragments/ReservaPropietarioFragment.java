package com.example.comparte.fragments;

/*
 * Clase ReservaPropietarioFragment
 *
 * Fragmento destinado a mostrar al propietario los detalles completos de una reserva recibida sobre una de sus habitaciones.
 * Presenta todos los datos relevantes de la solicitud realizada por un inquilino, incluyendo:
 * - Nombre del inquilino
 * - Descripción de la habitación
 * - Fechas de la reserva (inicio y fin)
 * - Fecha de solicitud
 * - Datos de contacto del inquilino (teléfono y correo)
 * - Estado actual de la reserva
 *
 * Además, permite al propietario **confirmar o rechazar la reserva** mediante botones de acción.
 * Al seleccionar una opción, se actualiza el estado en la base de datos y se notifica al usuario con un mensaje de confirmación.
 *
 * Este fragmento forma parte del flujo de gestión de reservas y es clave en el proceso de validación por parte del propietario.
 */


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Reserva;
import com.example.comparte.utils.SessionManager;

public class ReservaPropietarioFragment extends Fragment { // Clase ReservaPropietarioFragment que hereda de Fragment para mostrar las habitaciones disponibles.

    private TextView tvNombre, tvDescripcion, tvFecha, tvTelefono, tvEmail, tvFechaInicio, tvFechaFin, tvEstado;
    private Button btnConfirmar, btnRechazar;
    private DBComparte db;
    private SessionManager sessionManager;

    public ReservaPropietarioFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_reserva_propietario, container, false); // Inflar el layout del fragmento.

        // Inicializar vistas
        tvNombre = view.findViewById(R.id.tvNombreInquilino);
        tvDescripcion = view.findViewById(R.id.tvDescripcionHabitacion);
        tvFecha = view.findViewById(R.id.tvFechaReserva);
        tvTelefono = view.findViewById(R.id.tvTelefonoContacto);
        tvEmail = view.findViewById(R.id.tvemailContacto);
        tvFechaInicio = view.findViewById(R.id.tvFechaInicio);
        tvFechaFin = view.findViewById(R.id.tvFechaFin);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnRechazar = view.findViewById(R.id.btnRechazar);
        tvEstado = view.findViewById(R.id.tvEstado);

        db = new DBComparte(requireContext());

        // 🔹 Obtener la reserva enviada por el adapter
        Reserva reserva = (Reserva) getArguments().getSerializable("reserva"); // Obtener la reserva enviada por el adapter.

        if (reserva != null) { // Si la reserva no es nula.
            // Mostrar los datos en los campos
            tvNombre.setText(reserva.getNombreInquilino());
            tvDescripcion.setText(reserva.getDescripcionHabitacion());
            tvFecha.setText(reserva.getFechaReserva());
            tvTelefono.setText(reserva.getTelefonoInquilino());
            tvEmail.setText(reserva.getEmailInquilino());
            tvFechaInicio.setText(reserva.getFechaInicio());
            tvFechaFin.setText(reserva.getFechaFin());
            tvEstado.setText("Estado: " + reserva.getEstadoString());
        }

        // Confirmar reserva
        btnConfirmar.setOnClickListener(v -> {
            if (reserva != null && db.actualizarEstadoReserva(reserva.getIdReserva(), reserva.getIdHabitacion(), "CONFIRMADA")) {
                Toast.makeText(getContext(), "Reserva confirmada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al confirmar la reserva", Toast.LENGTH_SHORT).show();
            }
        });

        // Rechazar reserva
        btnRechazar.setOnClickListener(v -> {
            if (reserva != null && db.actualizarEstadoReserva(reserva.getIdReserva(), reserva.getIdHabitacion(), "RECHAZADA")) {
                Toast.makeText(getContext(), "Reserva rechazada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al rechazar la reserva", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
