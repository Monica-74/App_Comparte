package com.example.comparte.fragments;

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
import com.example.comparte.entities.EstadoReserva;
import com.example.comparte.entities.Reserva;
import com.example.comparte.utils.SessionManager;

public class ReservaPropietarioFragment extends Fragment {

    private TextView tvNombre, tvDescripcion, tvFecha, tvTelefono, tvEmail, tvFechaInicio, tvFechaFin;
    private Button btnConfirmar, btnRechazar;
    private DBComparte db;
    private SessionManager sessionManager;

    public ReservaPropietarioFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_reserva_propietario, container, false);

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

        db = new DBComparte(requireContext());

        // 🔹 Obtener la reserva enviada por el adapter
        Reserva reserva = (Reserva) getArguments().getSerializable("reserva");

        if (reserva != null) {
            // Mostrar los datos en los campos
            tvNombre.setText(reserva.getNombreInquilino());
            tvDescripcion.setText(reserva.getDescripcionHabitacion());
            tvFecha.setText(reserva.getFechaReserva());
            tvTelefono.setText(reserva.getTelefonoInquilino());
            tvEmail.setText(reserva.getEmailInquilino());
            tvFechaInicio.setText(reserva.getFechaInicio());
            tvFechaFin.setText(reserva.getFechaFin());
        }

        // 🔹 Confirmar reserva
        btnConfirmar.setOnClickListener(v -> {
            if (reserva != null && db.actualizarEstadoReserva(reserva.getIdInquilino(), reserva.getIdHabitacion(), "CONFIRMADA")) {
                Toast.makeText(getContext(), "Reserva confirmada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al confirmar la reserva", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔹 Rechazar reserva
        btnRechazar.setOnClickListener(v -> {
            if (reserva != null && db.actualizarEstadoReserva(reserva.getIdInquilino(), reserva.getIdHabitacion(), "RECHAZADA")) {
                Toast.makeText(getContext(), "Reserva rechazada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al rechazar la reserva", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
