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

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {
    public interface OnReservaClickListener {
        void onReservaClick(Reserva reserva);
    }

    private final List<Reserva> listaReservas;
    private final Context context;
    private final DBComparte db;
    private final OnReservaClickListener listener;

    public ReservaAdapter(Context context, List<Reserva> reservas, DBComparte db, OnReservaClickListener listener) {
        this.context = context;
        this.listaReservas = reservas;
        this.db = db;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva_propietario, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = listaReservas.get(position);

        holder.tvNombre.setText(reserva.getNombreInquilino());
        holder.tvDescripcion.setText(reserva.getDescripcionHabitacion());
        holder.tvFechaReserva.setText(reserva.getFechaReserva());
        holder.tvTelefono.setText(reserva.getTelefonoInquilino());
        holder.tvEmail.setText(reserva.getEmailInquilino());
        holder.tvFechaInicio.setText(reserva.getFechaInicio());
        holder.tvFechaFin.setText(reserva.getFechaFin());
        holder.tvEstado.setText(reserva.getEstado().toString());

        holder.btnConfirmar.setOnClickListener(v -> {
            boolean actualizado = db.actualizarEstadoReserva(
                    reserva.getIdInquilino(), reserva.getIdHabitacion(), EstadoReserva.CONFIRMADA.name()
            );
            if (actualizado) {
                reserva.setEstado(EstadoReserva.CONFIRMADA);
                notifyItemChanged(position);
                Toast.makeText(context, "Reserva confirmada", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnRechazar.setOnClickListener(v -> {
            boolean actualizado = db.actualizarEstadoReserva(
                    reserva.getIdInquilino(), reserva.getIdHabitacion(), EstadoReserva.RECHAZADA.name()
            );
            if (actualizado) {
                reserva.setEstado(EstadoReserva.RECHAZADA);
                notifyItemChanged(position);
                Toast.makeText(context, "Reserva rechazada", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReservaClick(reserva);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaReservas.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion, tvFechaReserva, tvTelefono, tvEmail, tvFechaInicio, tvFechaFin, tvEstado;
        Button btnConfirmar, btnRechazar;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreInquilino);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionHabitacion);
            tvFechaReserva = itemView.findViewById(R.id.tvFechaReserva);
            tvTelefono = itemView.findViewById(R.id.tvTelefonoContacto);
            tvEmail = itemView.findViewById(R.id.tvemailContacto);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            btnConfirmar = itemView.findViewById(R.id.btnConfirmar);
            btnRechazar = itemView.findViewById(R.id.btnRechazar);
        }
    }
}
