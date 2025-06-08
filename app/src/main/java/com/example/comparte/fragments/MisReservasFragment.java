package com.example.comparte.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.MisReservasAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Reserva;
import com.example.comparte.utils.SessionManager;

import java.util.List;

public class MisReservasFragment extends Fragment {

    private RecyclerView recyclerView;
    private MisReservasAdapter adapter;
    private DBComparte db;
    private SessionManager sessionManager;

    public MisReservasFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mis_reservas, container, false);

        recyclerView = view.findViewById(R.id.recyclerMisReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DBComparte(requireContext());
        sessionManager = new SessionManager(requireContext());

        int idInquilino = sessionManager.getInquilinoId();
        List<Reserva> reservas = db.obtenerReservasPorInquilino(idInquilino);

        if (reservas.isEmpty()) {
            Toast.makeText(getContext(), "No tienes reservas registradas", Toast.LENGTH_SHORT).show();
        }

        adapter = new MisReservasAdapter(requireContext(), reservas);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
