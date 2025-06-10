package com.example.comparte.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.EstadoReserva;
import com.example.comparte.entities.Reserva;

import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> { // Clase para el rol de inquilino y propietario y administrador de la lista de reservas realizadas por el usuario en la interfaz de la aplicación
    public interface OnReservaClickListener { // Interfaz para comunicar acciones de modificación y eliminación al fragmento o actividad que contiene el adaptador
        void onReservaClick(Reserva reserva); // Método para modificar una habitación o eliminar una habitación
    }

    private final List<Reserva> listaReservas;
    private final Context context;
    private final DBComparte db;
    private final OnReservaClickListener listener;

    public ReservaAdapter(Context context, List<Reserva> reservas, DBComparte db, OnReservaClickListener listener) { // Constructor de la clase ReservaAdapter que recibe una lista de reservas y el contexto de la aplicación y la base de datos
        this.context = context;
        this.listaReservas = reservas;
        this.db = db;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Método que se ejecuta al crear la vista de cada elemento de la lista de reservas y devuelve un objeto ReservaViewHolder con la vista inflada
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva_propietario, parent, false); // Infla el layout item_reserva_propietario para cada elemento de la lista de reservas
        return new ReservaViewHolder(view); // Devuelve un objeto ReservaViewHolder con la vista inflada
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) { // Método que se ejecuta al enlazar los datos de cada reserva con su vista correspondiente
        Reserva reserva = listaReservas.get(position); // Obtiene la reserva en la posición actual de la lista de reservas

        holder.tvNombre.setText(reserva.getNombreInquilino()); // Establece el nombre del inquilino en el TextView correspondiente
        holder.tvDescripcion.setText(reserva.getDescripcionHabitacion()); // Establece la descripción de la habitación en el TextView correspondiente
        holder.tvFechaReserva.setText(reserva.getFechaReserva()); // Establece la fecha de reserva en el TextView correspondiente
        holder.tvTelefono.setText(reserva.getTelefonoInquilino()); // Establece el teléfono del inquilino en el TextView correspondiente
        holder.tvEmail.setText(reserva.getEmailInquilino()); // Establece el email del inquilino en el TextView correspondiente
        holder.tvFechaInicio.setText(reserva.getFechaInicio()); // Establece la fecha de inicio en el TextView correspondiente
        holder.tvFechaFin.setText(reserva.getFechaFin()); // Establece la fecha de fin en el TextView correspondiente
        holder.tvEstado.setText(reserva.getEstado().toString()); // Establece el estado de la reserva en el TextView correspondiente


        holder.itemView.setOnClickListener(v -> { // Configura el evento de clic en la vista completa de la reserva
            if (listener != null) { // Comprueba si el listener no es nulo
                listener.onReservaClick(reserva); // Llama al método onReservaClick del listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaReservas.size();
    } // Método que devuelve el número de elementos en la lista de reservas

    public static class ReservaViewHolder extends RecyclerView.ViewHolder { // Clase para el rol de inquilino y propietario y administrador de la vista de cada elemento de la lista de reservas
        TextView tvNombre, tvDescripcion, tvFechaReserva, tvTelefono, tvEmail, tvFechaInicio, tvFechaFin, tvEstado;

        public ReservaViewHolder(@NonNull View itemView) { // Constructor de la clase ReservaViewHolder que recibe la vista de cada elemento de la lista de reservas
            super(itemView); // Llama al constructor de la clase padre RecyclerView.ViewHolder con la vista recibida
            tvNombre = itemView.findViewById(R.id.tvNombreInquilino);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionHabitacion);
            tvFechaReserva = itemView.findViewById(R.id.tvFechaReserva);
            tvTelefono = itemView.findViewById(R.id.tvTelefonoContacto);
            tvEmail = itemView.findViewById(R.id.tvemailContacto);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}
