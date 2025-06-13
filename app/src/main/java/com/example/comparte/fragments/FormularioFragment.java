package com.example.comparte.fragments;

/*
 * Clase FormularioFragment
 *
 * Fragmento encargado de gestionar el formulario para la creación o modificación de anuncios de habitaciones
 * por parte de los propietarios en la aplicación CompArte.
 *
 * Este fragmento permite al usuario introducir todos los datos necesarios para publicar una habitación, incluyendo:
 * - Título y descripción del anuncio
 * - Dirección, tipo de habitación y características (cama, baño, tamaño, etc.)
 * - Precio mensual
 * - Imagen ilustrativa de la habitación (seleccionada desde galería)
 *
 * También se encarga de validar los datos introducidos y almacenarlos en la base de datos SQLite,
 * relacionando la habitación con el ID del propietario correspondiente.
 *
 * Este formulario es esencial para permitir a los propietarios gestionar sus anuncios de forma sencilla y eficiente.
 */


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FormularioFragment extends Fragment { // Clase FormularioFragment que hereda de Fragment para gestionar el formulario de creación o modificación de habitaciones.

    private EditText etTitulo, etDescripcion, etDireccion, etPrecio,
            etCaracteristicaCama, etCaracteristicaBano, etCaracteristicaTamano, etTipo;
    private ImageView ivImagenSeleccionada;
    private Button btnSeleccionarImagen, btnGuardar;
    private ProgressBar progressBar;

    private byte[] imagenEnBytes;
    private Habitacion habitacionExistente;
    private DBComparte db;
    private SessionManager sessionManager;


    private final ActivityResultLauncher<Intent> imagenPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> { // Manejador para la selección de imagen desde la galería.
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                        ivImagenSeleccionada.setImageBitmap(bitmap);
                        ivImagenSeleccionada.setVisibility(View.VISIBLE);
                        imagenEnBytes = convertirBitmapABytes(bitmap);
                        ScrollView scrollView = requireView().findViewById(R.id.scrollView);
                        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al cargar imagen", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @SuppressLint("IntentReset")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_formulario, container, false); // Inflar el layout del fragmento.
        progressBar = view.findViewById(R.id.progressBar); // Inicialización de ProgressBar

        // Inicialización de vistas
        etTitulo = view.findViewById(R.id.etTitulo);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        etDireccion = view.findViewById(R.id.etDireccion);
        etPrecio = view.findViewById(R.id.etPrecio);
        etCaracteristicaCama = view.findViewById(R.id.etCaracteristicaCama);
        etCaracteristicaBano = view.findViewById(R.id.etCaracteristicaBano);
        etCaracteristicaTamano = view.findViewById(R.id.etCaracteristicaTamano);
        etTipo = view.findViewById(R.id.etTipo);
        ivImagenSeleccionada = view.findViewById(R.id.ivImagenSeleccionada);
        btnSeleccionarImagen = view.findViewById(R.id.btnSeleccionarImagen);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        db = new DBComparte(getContext());
        sessionManager = new SessionManager(getContext());

        // Si se pasa una habitación, cargar sus datos
        Bundle args = getArguments();
        if (args != null && args.containsKey("habitacion")) {
            habitacionExistente = (Habitacion) args.getSerializable("habitacion");
            cargarDatosHabitacion(habitacionExistente);
        }

        // Seleccionar imagen
        btnSeleccionarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            imagenPickerLauncher.launch(intent);
        });

        // Guardar habitación
        btnGuardar.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            btnGuardar.setEnabled(false);

            String titulo = etTitulo.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String precio = etPrecio.getText().toString().trim();
            String tipo = etTipo.getText().toString().trim();
            String cama = etCaracteristicaCama.getText().toString().trim();
            String bano = etCaracteristicaBano.getText().toString().trim();
            String tamano = etCaracteristicaTamano.getText().toString().trim();

            if (titulo.isEmpty() || precio.isEmpty() || direccion.isEmpty()) {
                Toast.makeText(getContext(), "Por favor rellena todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnGuardar.setEnabled(true);
                return;
            }
            if (imagenEnBytes == null) {
                Toast.makeText(getContext(), "Por favor, selecciona una imagen", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnGuardar.setEnabled(true);
                return;
            }

            Habitacion h = new Habitacion();
            h.setTitulo(titulo);
            h.setDescripcion(descripcion);
            h.setDireccion(direccion);
            h.setPrecio(precio);
            h.setTipo(tipo);
            h.setCaracteristicaCama(cama);
            h.setCaracteristicaBano(bano);
            h.setCaracteristicaTamano(tamano);
            h.setImagen(imagenEnBytes);
            int idPropietario = sessionManager.getPropietarioId();
            h.setIdPropietario(idPropietario);

            if (habitacionExistente != null) {
                h.setId(habitacionExistente.getId());
                db.actualizarHabitacion(h);
                Toast.makeText(getContext(), "Habitación actualizada", Toast.LENGTH_SHORT).show();
            } else {
                db.insertarHabitacion(h);
                Toast.makeText(getContext(), "Habitación creada", Toast.LENGTH_SHORT).show();
            }

            progressBar.setVisibility(View.GONE);
            btnGuardar.setEnabled(true);
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }


    private byte[] convertirBitmapABytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void cargarDatosHabitacion(Habitacion h) {
        etTitulo.setText(h.getTitulo());
        etDescripcion.setText(h.getDescripcion());
        etDireccion.setText(h.getDireccion());
        etPrecio.setText(h.getPrecio());
        etTipo.setText(h.getTipo());
        etCaracteristicaCama.setText(h.getCaracteristicaCama());
        etCaracteristicaBano.setText(h.getCaracteristicaBano());
        etCaracteristicaTamano.setText(h.getCaracteristicaTamano());

        byte[] imagen = h.getImagen();
        if (imagen != null && imagen.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
            ivImagenSeleccionada.setImageBitmap(bitmap);
            ivImagenSeleccionada.setVisibility(View.VISIBLE);
            imagenEnBytes = imagen;
        }
    }
}
