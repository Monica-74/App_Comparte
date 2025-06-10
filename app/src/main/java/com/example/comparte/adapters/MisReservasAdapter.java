package com.example.comparte.adapters;
/*
Clase MisReservasAdapter:gestiona la visualización y acciones de la lista de reservas para el rol de inquilino.
Adaptador que gestiona la visualización de la lista de reservas realizadas por el usuario.
 *
 * Funcionalidad principal:
 *  -Muestra los detalles de cada reserva (descripción, fechas y estado) en un RecyclerView.
 *
 * Características:
 * - Utiliza un ViewHolder personalizado (ReservaViewHolder) para enlazar los datos de cada reserva con su vista correspondiente.
 * - Recibe una lista de objetos Reserva y el contexto de la aplicación.
 * - Infla el layout item_mis_reservas para cada elemento de la lista.
 *
 * Uso:
 * - Diseñado para mostrar las reservas del usuario en la interfaz de la aplicación.
 * - Permite una visualización clara y ordenada de la información relevante de cada reserva.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.entities.Reserva;

import java.util.List;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ReservaViewHolder> { // Clase para el rol de inquilino y propietario y administrador de la lista de reservas realizadas por el usuario en la interfaz de la aplicación

    private final Context context;
    private final List<Reserva> reservas;

    public MisReservasAdapter(Context context, List<Reserva> reservas) { // Constructor de la clase MisReservasAdapter que recibe una lista de reservas y el contexto de la aplicación
        this.context = context;
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Método que se ejecuta al crear la vista de cada elemento de la lista de reservas
        View view = LayoutInflater.from(context).inflate(R.layout.item_mis_reservas, parent, false); // Infla el layout item_mis_reservas para cada elemento de la lista de reservas
        return new ReservaViewHolder(view); // Devuelve un objeto ReservaViewHolder con la vista inflada
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) { // Método que se ejecuta al enlazar los datos de cada reserva con su vista correspondiente
        Reserva reserva = reservas.get(position); // Obtiene la reserva en la posición actual de la lista de reservas

        holder.tvDescripcion.setText(reserva.getDescripcionHabitacion()); // Establece la descripción de la habitación en el TextView correspondiente
        holder.tvFechaReserva.setText("Fecha de reserva: " + reserva.getFechaReserva()); // Establece la fecha de reserva en el TextView correspondiente
        holder.tvFechaInicio.setText("Inicio: " + reserva.getFechaInicio()); // Establece la fecha de inicio en el TextView correspondiente
        holder.tvFechaFin.setText("Fin: " + reserva.getFechaFin()); // Establece la fecha de fin en el TextView correspondiente
        holder.tvEstado.setText("Estado: " + reserva.getEstado().name()); // Establece el estado de la reserva en el TextView correspondiente
    }

    @Override
    public int getItemCount() { // Método que devuelve el número de elementos en la lista de reservas
        return reservas.size(); // Devuelve el número de elementos en la lista de reservas
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder { // Clase para el rol de inquilino y propietario y administrador de la vista de cada elemento de la lista de reservas
        TextView tvDescripcion, tvFechaReserva, tvFechaInicio, tvFechaFin, tvEstado;

        public ReservaViewHolder(@NonNull View itemView) { // Constructor de la clase ReservaViewHolder que recibe la vista de cada elemento de la lista de reservas
            super(itemView); // Llama al constructor de la clase padre RecyclerView.ViewHolder con la vista recibida
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionHabitacion); // Obtiene el TextView correspondiente a la descripción de la habitación
            tvFechaReserva = itemView.findViewById(R.id.tvFechaReserva); // Obtiene el TextView correspondiente a la fecha de reserva de la habitación
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio); // Obtiene el TextView correspondiente a la fecha de inicio de la habitación en la reserva
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin); // Obtiene el TextView correspondiente a la fecha de fin de la habitación en la reserva
            tvEstado = itemView.findViewById(R.id.tvEstado); // Obtiene el TextView correspondiente al estado de la reserva de la habitación
        }
    }
}
