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

        etNombre = view.findViewById(R.id.nombreReserva);
        etDescripcion = view.findViewById(R.id.descripcionReserva);
        etFecha = view.findViewById(R.id.fechaReserva);
        etTelefono = view.findViewById(R.id.telefonoReserva);
        etEmail = view.findViewById(R.id.emailReserva);
        btnConfirmarReserva = view.findViewById(R.id.btnConfirmarReserva);

        db = new DBComparte(getContext());
        sessionManager = new SessionManager(getContext());

        // Abrir calendario al hacer clic en fecha
        etFecha.setOnClickListener(v -> mostrarDatePicker());

        Button btnConfirmarReserva = view.findViewById(R.id.btnConfirmarReserva);

        btnConfirmarReserva.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int idInquilino = sessionManager.getInquilinoId(); // Asegúrate de tener este método en SessionManager
            int idHabitacion = getArguments().getInt("idHabitacion", -1); // Recibido al navegar

            if (idHabitacion == -1) {
                Toast.makeText(getContext(), "Error: no se recibió el ID de la habitación", Toast.LENGTH_SHORT).show();
                return;
            }

            Reserva reserva = new Reserva();
            reserva.setNombreInquilino(nombre);
            reserva.setDescripcionHabitacion(descripcion);
            reserva.setFechaReserva(fecha);
            reserva.setTelefonoInquilino(telefono);
            reserva.setEmailInquilino(email);
            reserva.setIdInquilino(idInquilino);
            reserva.setIdHabitacion(idHabitacion);
            reserva.setEstado(EstadoReserva.PENDIENTE);

            DBComparte db = new DBComparte(requireContext());

            if (db.insertarReserva(reserva)) {
                Toast.makeText(getContext(), "Reserva realizada correctamente", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).popBackStack(); // volver atrás
            } else {
                Toast.makeText(getContext(), "Error al guardar reserva", Toast.LENGTH_SHORT).show();
            }
        });

        

        return view;
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                    etFecha.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );
        datePickerDialog.show();
    }
}
