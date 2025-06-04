package com.example.comparte.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.comparte.R;
/*
esta clase es para los propietarios, recordar
 */

public class PropietarioFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        try {
            view = inflater.inflate(R.layout.fragment_propietario, container, false);

            Button btnPublicidad = view.findViewById(R.id.btnPublicidad);
            btnPublicidad.setOnClickListener(v ->
                    Navigation.findNavController(view).navigate(R.id.action_propietarioFragment_to_formularioFragment)
            );
            Button btnVerAnuncios = view.findViewById(R.id.btnVerAnuncio);
            btnVerAnuncios.setOnClickListener(v -> {
                //Navigation.findNavController(view).navigate(R.id.action_propietarioFragment_to_habitacionesPropietarioFragment);
                Navigation.findNavController(view).navigate(R.id.habitacionFragment);

            });

            return view;
        } catch (Exception e) {
            Log.e("PropietarioFragment", "Error al inflar layout", e);
            Toast.makeText(getContext(), "Error cargando la vista: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
