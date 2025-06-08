package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.comparte.R;

public class InquilinoFragment extends Fragment {

    public InquilinoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inquilino, container, false);

        Button btnBuscar = view.findViewById(R.id.btnBuscarHabitacion);
        btnBuscar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.habitacionesDisponiblesFragment);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button btnMisReservas = view.findViewById(R.id.btnMisReservas);

        btnMisReservas.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_inquilinoFragment_to_misReservasFragment);
        });

        return view;
    }
}
