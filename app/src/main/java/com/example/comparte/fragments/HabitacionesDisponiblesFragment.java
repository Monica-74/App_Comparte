package com.example.comparte.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;

import java.util.List;
/*
esta clase es para los inquilinos, recordar
 */

public class HabitacionesDisponiblesFragment extends Fragment {

    private RecyclerView recyclerView;

    public HabitacionesDisponiblesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_habitaciones_propietario, container, false); // Reutiliza layout

        recyclerView = view.findViewById(R.id.recyclerHabitaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DBComparte db = new DBComparte(getContext());
        List<Habitacion> habitaciones = db.getHabitacionesDisponibles();

        HabitacionAdapter adapter = new HabitacionAdapter(getContext(), habitaciones, null);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
