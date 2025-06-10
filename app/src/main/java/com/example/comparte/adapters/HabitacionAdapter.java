package com.example.comparte.adapters;
/*
Clase Habitacion Adapter:gestiona la visualización y acciones de la lista de habitaciones para el rol de propietario.
 *
 * Funcionalidades principales:
 * Muestra los datos de cada habitación en un RecyclerView.
 * Permite modificar o eliminar habitaciones mediante botones, visibles solo si el propietario tiene permisos.
 * Gestiona la navegación hacia el formulario de edición y el detalle de la habitación.
 * Confirma la eliminación de un anuncio con un diálogo y actualiza la base de datos y la lista.
 * Muestra la imagen de la habitación o una imagen por defecto si no hay disponible.
 * Implementa una interfaz para comunicar acciones de modificación y eliminación al fragmento o actividad que contiene el adaptador.

 */

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.viewHolder.HabitacionViewHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionViewHolder> { // Clase para el rol de propietario y inquilino

    private final List<Habitacion> lista;
    private final Context context;
    private final OnHabitacionActionListener listener;
    private final boolean mostrarBotones;

    public interface OnHabitacionActionListener { // Interfaz para comunicar acciones de modificación y eliminación al fragmento o actividad que contiene el adaptador
        void modificarHabitacion(Habitacion habitacion); // Método para modificar una habitación
        void eliminarHabitacion(Habitacion habitacion); //  Método para eliminar una habitación
    }

    public HabitacionAdapter(Context context, List<Habitacion> lista, OnHabitacionActionListener listener, boolean mostrarBotones) { // Constructor de la clase HabitacionAdapter
        this.context = context;
        this.lista = lista;
        this.listener = listener;
        this.mostrarBotones = mostrarBotones;
    }

    @Override
    public HabitacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Método que se ejecuta al crear la vista
        View view = LayoutInflater.from(context).inflate(R.layout.item_habitacion, parent, false); // Infla el layout del item de la habitación
        return new HabitacionViewHolder(view); // Devuelve un objeto HabitacionViewHolder con la vista inflada
    }

    @Override
    public void onBindViewHolder(HabitacionViewHolder holder, int position) { // Método que se ejecuta al vincular la vista con los datos
        Habitacion habitacion = lista.get(position); // Obtiene la habitación en la posición actual de la lista

        holder.getTituloHabitacion().setText(habitacion.getTitulo()); // Establece el título de la habitación en el TextView correspondiente
        holder.getDireccionHabitacion().setText(habitacion.getDireccion()); // Establece la dirección de la habitación en el TextView correspondiente
        holder.getPrecioHabitacion().setText(habitacion.getPrecio()); // Establece el precio de la habitación en el TextView correspondiente
        holder.getDescripcionHabitacion().setText(habitacion.getDescripcion()); // Establece la descripción de la habitación en el TextView correspondiente
        holder.getCaracteristicaCama().setText(habitacion.getCaracteristicaCama()); // Establece la característica de la cama de la habitación en el TextView correspondiente
        holder.getCaracteristicaBano().setText(habitacion.getCaracteristicaBano()); // Establece la característica del baño de la habitación en el TextView correspondiente
        holder.getCaracteristicaTamano().setText(habitacion.getCaracteristicaTamano()); // Establece la característica del tamaño de la habitación en el TextView correspondiente

        String telefono = habitacion.getTelefonoContacto(); // Obtiene el teléfono de contacto de la habitación
        if (telefono == null || telefono.trim().isEmpty()) { // Comprueba si el teléfono de contacto es nulo o vacío
            holder.getTelefonoContacto().setText("Teléfono no disponible"); // Establece el texto del TextView correspondiente
        } else { // Si el teléfono de contacto no es nulo o vacío
            holder.getTelefonoContacto().setText("Teléfono: " + telefono); // Establece el texto del TextView correspondiente
        }

        Log.d("TELEFONO_DEBUG", "Teléfono cargado: " + habitacion.getTelefonoContacto()); // Registra el teléfono de contacto en el log


        byte[] imagenBytes = habitacion.getImagen(); // Obtiene la imagen de la habitación
        if (imagenBytes != null && imagenBytes.length > 0) { // Comprueba si la imagen de la habitación no es nula o vacía
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length); // Convierte la imagen de bytes a un objeto Bitmap
            holder.getImageHabitacion().setImageBitmap(bitmap); // Establece la imagen en el ImageView correspondiente
        } else { // Si la imagen de la habitación es nula o vacía
            holder.getImageHabitacion().setImageResource(R.drawable.foto1); // Establece una imagen por defecto en el ImageView correspondiente
        }

        if (mostrarBotones) { // Comprueba si se deben mostrar los botones de modificación y eliminación
            holder.getBtnModificar().setVisibility(View.VISIBLE); // Establece la visibilidad del botón de modificación en el TextView correspondiente
            holder.getBtnEliminar().setVisibility(View.VISIBLE); // Establece la visibilidad del botón de eliminación en el TextView correspondiente
        } else {
            holder.getBtnModificar().setVisibility(View.GONE);
            holder.getBtnEliminar().setVisibility(View.GONE);
        }

        holder.getBtnModificar().setOnClickListener(v -> { // Configura el evento de clic en el botón de modificación
            Bundle bundle = new Bundle(); // Crea un objeto Bundle para pasar datos entre fragmentos
            bundle.putSerializable("habitacion", habitacion); // Agrega la habitación a los datos del Bundle
            Navigation.findNavController(v).navigate(R.id.formularioFragment, bundle); // Navega al fragmento de formulario con los datos de la habitación
        });

        holder.getBtnEliminar().setOnClickListener(v -> { // Configura el evento de clic en el botón de eliminación
            new AlertDialog.Builder(context) // Crea un diálogo de confirmación
                    .setTitle("Confirmar eliminación") // Establece el título del diálogo
                    .setMessage("¿Estás segur@ de que quieres eliminar el anuncio?") // Establece el mensaje del diálogo
                    .setPositiveButton("Eliminar", (dialog, which) -> { // Configura el evento de clic en el botón de confirmación
                        DBComparte db = new DBComparte(context); // Crea un objeto DBComparte para interactuar con la base de datos
                        db.eliminarHabitacion(habitacion.getId()); // Elimina la habitación de la base de datos
                        lista.remove(position); // Elimina la habitación de la lista
                        notifyItemRemoved(position); // Notifica al adaptador que se ha eliminado un elemento de la lista
                        Toast.makeText(context, "Anuncio eliminado con éxito", Toast.LENGTH_SHORT).show(); // Muestra un mensaje de éxito

                        if (listener != null) { // Comprueba si el listener no es nulo
                            listener.eliminarHabitacion(habitacion); // Llama al método eliminarHabitacion del listener
                        }
                    })
                    .setNegativeButton("Cancelar", null) // Configura el evento de clic en el botón de cancelación
                    .show(); // Muestra el diálogo de confirmación
        });

        holder.itemView.setOnClickListener(v -> { // Configura el evento de clic en la vista completa de la habitación
            File tempFile = new File(context.getCacheDir(), "habitacion_temp.jpg"); // Crea un archivo temporal para la imagen de la habitación
            try (FileOutputStream fos = new FileOutputStream(tempFile)) { // Guarda la imagen de la habitación en el archivo temporal
                fos.write(habitacion.getImagen()); // Escribe la imagen en el archivo temporal
                fos.flush(); // Vaciar el flujo de salida
            } catch (IOException e) { // Maneja la excepción en caso de error al guardar la imagen
                e.printStackTrace(); // Imprime el mensaje de error en la consola
            }

            Bundle bundle = new Bundle(); // Crea un objeto Bundle para pasar datos entre fragmentos
            bundle.putString("rutaImagen", tempFile.getAbsolutePath()); // Agrega la ruta de la imagen a los datos del Bundle
            bundle.putString("titulo", habitacion.getTitulo()); // Agrega el título de la habitación a los datos del Bundle
            bundle.putString("descripcion", habitacion.getDescripcion()); // Agrega la descripción de la habitación a los datos del Bundle
            bundle.putString("direccion", habitacion.getDireccion()); // Agrega la dirección de la habitación a los datos del Bundle
            bundle.putString("precio", habitacion.getPrecio()); // Agrega el precio de la habitación a los datos del Bundle
            bundle.putString("caracteristicaCama", habitacion.getCaracteristicaCama()); // Agrega la característica de la cama de la habitación a los datos del Bundle
            bundle.putString("caracteristicaBano", habitacion.getCaracteristicaBano()); // Agrega la característica del baño de la habitación a los datos del Bundle
            bundle.putString("caracteristicaTamano", habitacion.getCaracteristicaTamano()); // Agrega la característica del tamaño de la habitación a los datos del Bundle
            bundle.putString("tipo", habitacion.getTipo()); // Agrega el tipo de la habitación a los datos del Bundle
            bundle.putInt("idPropietario", habitacion.getIdPropietario()); // Agrega el id del propietario de la habitación a los datos del Bundle
            bundle.putSerializable("habitacion", habitacion); // Agrega la habitación a los datos del Bundle

            Navigation.findNavController(v).navigate(R.id.detalleHabitacionFragment, bundle); // Navega al fragmento de detalle de la habitación con los datos de la habitación
        });
    }

    @Override
    public int getItemCount() { // Método que devuelve el número de elementos en la lista
        return lista.size(); // Devuelve el número de elementos en la lista
    }
}
