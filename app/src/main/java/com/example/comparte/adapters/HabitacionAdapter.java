package com.example.comparte.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.models.Habitacion;
import com.example.comparte.viewHolder.HabitacionViewHolder;

import java.util.List;

public class HabitacionAdapter  extends  RecyclerView.Adapter<HabitacionViewHolder> {

    private Context context;
    private List<Habitacion> listaHabitaciones;

    public HabitacionAdapter(Context context, List<Habitacion> listaHabitaciones) {
        this.context = context;
        this.listaHabitaciones = listaHabitaciones;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HabitacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habitacion, parent, false);
        return new HabitacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HabitacionViewHolder holder, int position) {
        Habitacion habitacion = listaHabitaciones.get(position);
        holder.getTituloHabitacion().setText(habitacion.getTitulo());
        holder.getDireccionHabitacion().setText(habitacion.getDireccion());
        holder.getCaracteristicaCama().setText(habitacion.getCaracteristicaCama());
        holder.getCaracteristicaBano().setText(habitacion.getCaracteristicaBano());
        holder.getCaracteristicaTamano().setText(habitacion.getCaracteristicaTamano());
        holder.getPrecioHabitacion().setText(habitacion.getPrecio());
        holder.getDescripcionHabitacion().setText(habitacion.getDescripcion());
        byte[] imageBytes = habitacion.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.getImageHabitacion().setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return listaHabitaciones.size();
    }
}

