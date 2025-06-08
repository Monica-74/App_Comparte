package com.example.comparte.adapters;

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

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ReservaViewHolder> {

    private final Context context;
    private final List<Reserva> reservas;

    public MisReservasAdapter(Context context, List<Reserva> reservas) {
        this.context = context;
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mis_reservas, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);

        holder.tvDescripcion.setText(reserva.getDescripcionHabitacion());
        holder.tvFechaReserva.setText("Fecha de reserva: " + reserva.getFechaReserva());
        holder.tvFechaInicio.setText("Inicio: " + reserva.getFechaInicio());
        holder.tvFechaFin.setText("Fin: " + reserva.getFechaFin());
        holder.tvEstado.setText("Estado: " + reserva.getEstado().name());
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescripcion, tvFechaReserva, tvFechaInicio, tvFechaFin, tvEstado;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionHabitacion);
            tvFechaReserva = itemView.findViewById(R.id.tvFechaReserva);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}
