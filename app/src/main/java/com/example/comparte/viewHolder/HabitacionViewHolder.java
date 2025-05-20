package com.example.comparte.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;

public class HabitacionViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageHabitacion;
    private TextView tituloHabitacion;
    private TextView direccionHabitacion;
    private TextView caracteristicaCama;
    private TextView caracteristicaBano;
    private TextView caracteristicaTamano;
    private TextView precioHabitacion;
    private TextView descripcionHabitacion;


    public HabitacionViewHolder(View item_habitacion_view) {
        super(item_habitacion_view);

        this.imageHabitacion = itemView.findViewById(R.id.imageHabitacion);
        this.tituloHabitacion = itemView.findViewById(R.id.tituloHabitacion);
        this.direccionHabitacion = itemView.findViewById(R.id.direccionHabitacion);
        this.caracteristicaCama = itemView.findViewById(R.id.caracteristicaCama);
        this.caracteristicaBano = itemView.findViewById(R.id.caracteristicaBano);
        this.caracteristicaTamano = itemView.findViewById(R.id.caracteristicaTamano);
        this.precioHabitacion = itemView.findViewById(R.id.precioHabitacion);
        this.descripcionHabitacion = itemView.findViewById(R.id.descripcionHabitacion);
    }



    public ImageView getImageHabitacion() {
        return imageHabitacion;
    }

    public void setImageHabitacion(ImageView imageHabitacion) {
        this.imageHabitacion = imageHabitacion;
    }

    public TextView getTituloHabitacion() {
        return tituloHabitacion;
    }

    public void setTituloHabitacion(TextView tituloHabitacion) {
        this.tituloHabitacion = tituloHabitacion;
    }

    public TextView getDireccionHabitacion() {
        return direccionHabitacion;
    }

    public void setDireccionHabitacion(TextView direccionHabitacion) {
        this.direccionHabitacion = direccionHabitacion;
    }

    public TextView getCaracteristicaCama() {
        return caracteristicaCama;
    }

    public void setCaracteristicaCama(TextView caracteristicaCama) {
        this.caracteristicaCama = caracteristicaCama;
    }

    public TextView getCaracteristicaBano() {
        return caracteristicaBano;
    }

    public void setCaracteristicaBano(TextView caracteristicaBano) {
        this.caracteristicaBano = caracteristicaBano;
    }

    public TextView getCaracteristicaTamano() {
        return caracteristicaTamano;
    }

    public void setCaracteristicaTamano(TextView caracteristicaTamano) {
        this.caracteristicaTamano = caracteristicaTamano;
    }

    public TextView getPrecioHabitacion() {
        return precioHabitacion;
    }

    public void setPrecioHabitacion(TextView precioHabitacion) {
        this.precioHabitacion = precioHabitacion;
    }

    public TextView getDescripcionHabitacion() {
        return descripcionHabitacion;
    }

    public void setDescripcionHabitacion(TextView descripcionHabitacion) {
        this.descripcionHabitacion = descripcionHabitacion;
    }
}
