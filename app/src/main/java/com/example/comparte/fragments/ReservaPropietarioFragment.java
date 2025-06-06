package com.example.comparte.fragments;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

public class ReservaPropietarioFragment extends Fragment {

    private EditText etNombre, etDescripcion, etFecha, etTelefono, etEmail, etFechaInicio, etFechaFin;
    private Button btnConfirmar;
    private Button btnRechazar;
    private DBComparte db;
    private SessionManager sessionManager;

    public ReservaPropietarioFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserva_inquilino, container, false);

        // Inicializar vistas
        etNombre = view.findViewById(R.id.nombreReserva);
        etDescripcion = view.findViewById(R.id.descripcionReserva);
        etFecha = view.findViewById(R.id.fechaReserva);
        etTelefono = view.findViewById(R.id.telefonoReserva);
        etEmail = view.findViewById(R.id.emailReserva);
        etFechaInicio = view.findViewById(R.id.fechaInicioReserva);
        etFechaFin = view.findViewById(R.id.fechaFinReserva);
        btnConfirmar = view.findViewById(R.id.btnConfirmarReserva);

        db = new DBComparte(requireContext());
        sessionManager = new SessionManager(requireContext());

        // DatePicker para campos de fecha
        etFecha.setOnClickListener(v -> mostrarDatePicker(etFecha));
        etFechaInicio.setOnClickListener(v -> mostrarDatePicker(etFechaInicio));
        etFechaFin.setOnClickListener(v -> mostrarDatePicker(etFechaFin));

        btnConfirmar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String fechaInicio = etFechaInicio.getText().toString().trim();
            String fechaFin = etFechaFin.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || telefono.isEmpty() || email.isEmpty()
                    || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int idInquilino = sessionManager.getInquilinoId(); // Asegúrate de que esté bien definido
            int idHabitacion = sessionManager.getHabitacionIdSeleccionada();

            Reserva reserva = new Reserva();
            reserva.setNombreInquilino(nombre);
            reserva.setDescripcionHabitacion(descripcion);
            reserva.setFechaReserva(fecha);
            reserva.setTelefonoInquilino(telefono);
            reserva.setEmailInquilino(email);
            reserva.setIdInquilino(idInquilino);
            reserva.setIdHabitacion(idHabitacion);
            reserva.setEstado(EstadoReserva.valueOf("CONFIRMADA")); // Aquí el propietario confirma la reserva

            if (db.actualizarEstadoReserva(idInquilino, idHabitacion, "confirmada")) {
                Toast.makeText(getContext(), "Reserva confirmada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al confirmar la reserva", Toast.LENGTH_SHORT).show();
            }
        });
        btnRechazar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int idInquilino = sessionManager.getInquilinoId(); // Asegúrate de tener este método
            int idHabitacion = sessionManager.getHabitacionIdSeleccionada(); // También necesitas guardar este dato

            Reserva reserva = new Reserva();
            reserva.setNombreInquilino(nombre);
            reserva.setDescripcionHabitacion(descripcion);
            reserva.setFechaReserva(fecha);
            reserva.setTelefonoInquilino(telefono);
            reserva.setEmailInquilino(email);
            reserva.setIdInquilino(idInquilino);
            reserva.setIdHabitacion(idHabitacion);
            reserva.setEstado(EstadoReserva.valueOf("pendiente"));

            db.insertarReserva(reserva);

            if (db.insertarReserva(reserva)) {
                Toast.makeText(getContext(), "Reserva rechazada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al rechazar la reserva", Toast.LENGTH_SHORT).show();
            }


            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }

    private void mostrarDatePicker(EditText campoFecha) {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                    campoFecha.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );
        datePickerDialog.show();
    }
}
