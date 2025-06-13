package com.example.comparte.fragments;

/*
 * Clase DetalleFragment
 *
 * Fragmento encargado de mostrar la información detallada de una habitación seleccionada por el inquilino.
 * Proporciona una vista ampliada del anuncio con todos sus atributos relevantes, como:
 * - Título y descripción
 * - Imagen ilustrativa
 * - Dirección y tipo de habitación
 * - Precio, características y tipo de convivencia
 * - Datos de contacto del propietario (teléfono, email, etc.)
 *
 * Además, puede incluir botones de acción como:
 * - "Volver": para regresar al listado de habitaciones disponibles.
 * - "Reservar esta habitación": para iniciar el proceso de reserva desde este fragmento.
 *
 * Este fragmento permite al usuario tomar una decisión informada antes de realizar una solicitud de reserva.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.utils.SessionManager;

public class DetalleHabitacionFragment extends Fragment { // Clase DetalleFragment que hereda de Fragment para mostrar el detalle de una habitación seleccionada.

    private TextView tvTitulo, tvDescripcion, tvDireccion, tvPrecio,
            tvCaracteristicaCama, tvCaracteristicaBano, tvCaracteristicaTamano, tvTipo;
    private ImageView ivDetalleImagen;
    private Button btnVolver, btnModificar, btnEliminar;

    private Habitacion habitacion;
    private SessionManager sessionManager;

    public DetalleHabitacionFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {  // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_detalle_habitacion, container, false); // Inflar el layout del fragmento.

        // Enlazar vistas
        ivDetalleImagen = view.findViewById(R.id.ivDetalleImagen);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvDescripcion = view.findViewById(R.id.tvDescripcion);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvPrecio = view.findViewById(R.id.tvPrecio);
        tvCaracteristicaCama = view.findViewById(R.id.tvCaracteristicaCama);
        tvCaracteristicaBano = view.findViewById(R.id.tvCaracteristicaBano);
        tvCaracteristicaTamano = view.findViewById(R.id.tvCaracteristicaTamano);
        tvTipo = view.findViewById(R.id.tvTipo);
        btnVolver = view.findViewById(R.id.btnVolver);
        btnModificar = view.findViewById(R.id.btnModificar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        // Ocultar botones por defecto
        btnModificar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);

        // Obtener datos del Bundle


        Bundle args = getArguments();
        if (args != null) {
            habitacion = (Habitacion) args.getSerializable("habitacion");

            if (habitacion != null) {
                tvTitulo.setText(habitacion.getTitulo());
                tvDescripcion.setText("Descripción: " + habitacion.getDescripcion());
                tvDireccion.setText("Dirección: " + habitacion.getDireccion());
                tvPrecio.setText(String.valueOf("Precio: " + habitacion.getPrecio()));
                tvCaracteristicaCama.setText("Cama: " + habitacion.getCaracteristicaCama());
                tvCaracteristicaBano.setText("Baño: " + habitacion.getCaracteristicaBano());
                tvCaracteristicaTamano.setText("Tamaño: " + habitacion.getCaracteristicaTamano());
                tvTipo.setText("Tipo: " + habitacion.getTipo());
            }
        }


        // Imagen desde ruta
            String rutaImagen = args.getString("rutaImagen");
            if (rutaImagen != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
                ivDetalleImagen.setImageBitmap(bitmap);
            }

            // También obtener el objeto completo si lo has pasado (para eliminar o modificar)
            habitacion = (Habitacion) args.getSerializable("habitacion");


        // Mostrar teléfono del propietario si existe
        int idPropietario = args.getInt("idPropietario", -1);
        if (idPropietario != -1) {
            DBComparte db = new DBComparte(requireContext());
            String telefono = db.obtenerTelefonoPropietario(idPropietario);

            TextView txtTelefono = view.findViewById(R.id.tvTelefonoContacto);
            txtTelefono.setText("Teléfono de contacto: " + telefono);
        }


        // Obtener rol del usuario
        sessionManager = new SessionManager(requireContext());
        String rol = sessionManager.getRol();

        Log.d("ROL_DEBUG", "Rol actual desde SessionManager: " + rol);
        Toast.makeText(getContext(), "ROL: " + rol, Toast.LENGTH_SHORT).show();

        // Si es propietario, mostrar botones
        if ("propietario".equalsIgnoreCase(rol)) { // Si el rol es propietario, mostrar botones de modificación y eliminación de habitación
            btnModificar.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);

            btnModificar.setOnClickListener(v -> { // Acción botón modificar habitación
                if (habitacion != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("habitacion", habitacion);
                    Navigation.findNavController(v).navigate(R.id.action_detalleHabitacionFragment_to_formularioFragment, bundle);
                }
            });

            btnEliminar.setOnClickListener(v -> { // Acción botón eliminar habitación
                if (habitacion != null) {
                    DBComparte db = new DBComparte(requireContext());
                    db.eliminarHabitacion(habitacion.getId());
                    Toast.makeText(getContext(), "Habitación eliminada con éxito", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(v).navigate(R.id.propietarioFragment);
                }
            });
        }

        // Acción botón volver
        btnVolver.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        Button btnReservar = view.findViewById(R.id.btnReservar); // Botón para reservar habitación en detalle de habitación

        btnReservar.setVisibility(View.GONE); // oculto por defecto
        if ("inquilino".equalsIgnoreCase(rol)) {
            btnReservar.setVisibility(View.VISIBLE);

            btnReservar.setOnClickListener(v -> {
                if (habitacion != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idHabitacion", habitacion.getId());
                    Navigation.findNavController(v).navigate(R.id.action_detalleHabitacionFragment_to_reservaFragment, bundle);
                }
            });
        }
            return view;
    }
}
