package com.example.comparte.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;

import java.util.List;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionAdapter.ViewHolder> {

    private final List<Habitacion> lista;
    private final Context context;
    private final OnHabitacionActionListener listener;

    public interface OnHabitacionActionListener {
        void modificarHabitacion(Habitacion habitacion);
        void eliminarHabitacion(Habitacion habitacion);
    }

    public HabitacionAdapter(Context context, List<Habitacion> lista, OnHabitacionActionListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habitacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Habitacion habitacion = lista.get(position);

        holder.titulo.setText(habitacion.getTitulo());
        holder.direccion.setText(habitacion.getDireccion());
        holder.precio.setText(habitacion.getPrecio());
        holder.descripcion.setText(habitacion.getDescripcion());
        holder.caracteristicaCama.setText(habitacion.getCaracteristicaCama());
        holder.caracteristicaBano.setText(habitacion.getCaracteristicaBano());
        holder.caracteristicaTamano.setText(habitacion.getCaracteristicaTamano());

        // Si tienes una librería como Glide o Picasso, puedes cargar la imagen desde byte[]
        // holder.imagen.setImageBitmap(...);

        holder.btnModificar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("habitacion", habitacion); // pasamos la habitación

            Navigation.findNavController(v).navigate(R.id.formularioFragment, bundle);
        });


        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás segur@ de que quieres eliminar el anuncio?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        DBComparte db = new DBComparte(context);
                        db.eliminarHabitacion(habitacion.getId());
                        lista.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Anuncio eliminado con éxito", Toast.LENGTH_SHORT).show();

                        // Redirigir al propietarioFragment (si es necesario)
                        if (listener != null) {
                            listener.eliminarHabitacion(habitacion);
//                            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
//                            navController.navigate(R.id.action_habitacionesPropietarioFragment_to_propietarioFragment);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView titulo, direccion, precio, descripcion, caracteristicaCama, caracteristicaBano, caracteristicaTamano;
        Button btnModificar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imageHabitacion);
            titulo = itemView.findViewById(R.id.tituloHabitacion);
            direccion = itemView.findViewById(R.id.direccionHabitacion);
            precio = itemView.findViewById(R.id.precioHabitacion);
            descripcion = itemView.findViewById(R.id.descripcionHabitacion);
            caracteristicaCama = itemView.findViewById(R.id.caracteristicaCama);
            caracteristicaBano = itemView.findViewById(R.id.caracteristicaBano);
            caracteristicaTamano = itemView.findViewById(R.id.caracteristicaTamano);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
