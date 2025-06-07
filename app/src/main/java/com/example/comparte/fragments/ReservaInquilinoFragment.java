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

public class ReservaInquilinoFragment extends Fragment {

    private EditText etNombre, etDescripcion, etFecha, etTelefono, etEmail;
    private EditText etFechaInicio, etFechaFin;
    private DBComparte db;
    private SessionManager sessionManager;
    private Button btnConfirmarReserva;

    public ReservaInquilinoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserva_inquilino, container, false);

        // Enlazar campos
        etNombre = view.findViewById(R.id.nombreReserva);
        etDescripcion = view.findViewById(R.id.descripcionReserva);
        etFecha = view.findViewById(R.id.fechaReserva);
        etTelefono = view.findViewById(R.id.telefonoReserva);
        etEmail = view.findViewById(R.id.emailReserva);
        etFechaInicio = view.findViewById(R.id.fechaInicioReserva);
        etFechaFin = view.findViewById(R.id.fechaFinReserva);
        btnConfirmarReserva = view.findViewById(R.id.btnConfirmarReserva);

        db = new DBComparte(getContext());
        sessionManager = new SessionManager(getContext());

        // Mostrar date pickers
        etFecha.setOnClickListener(v -> mostrarDatePicker(etFecha));
        etFechaInicio.setOnClickListener(v -> mostrarDatePicker(etFechaInicio));
        etFechaFin.setOnClickListener(v -> mostrarDatePicker(etFechaFin));

        // Botón confirmar reserva
        btnConfirmarReserva.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String fechaInicio = etFechaInicio.getText().toString().trim();
            String fechaFin = etFechaFin.getText().toString().trim();

            // Validación
            if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() ||
                    telefono.isEmpty() || email.isEmpty() ||
                    fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int idInquilino = sessionManager.getInquilinoId();
            int idHabitacion = getArguments().getInt("idHabitacion", -1);

            if (idHabitacion == -1) {
                Toast.makeText(getContext(), "Error: no se recibió el ID de la habitación", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear reserva
            Reserva reserva = new Reserva();
            reserva.setNombreInquilino(nombre);
            reserva.setDescripcionHabitacion(descripcion);
            reserva.setFechaReserva(fecha);
            reserva.setTelefonoInquilino(telefono);
            reserva.setEmailInquilino(email);
            reserva.setIdInquilino(idInquilino);
            reserva.setIdHabitacion(idHabitacion);
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fechaFin);
            reserva.setEstado(EstadoReserva.PENDIENTE);

            if (db.insertarReserva(reserva)) {
                Toast.makeText(getContext(), "Reserva realizada correctamente", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al guardar reserva", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Método genérico para mostrar el DatePicker y rellenar el campo correspondiente
    private void mostrarDatePicker(EditText campo) {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                    campo.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );
        datePickerDialog.show();
    }
}
