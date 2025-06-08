package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.ReservaAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Reserva;
import com.example.comparte.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ListadoReservasFragment extends Fragment {

    private RecyclerView recyclerView;
    private DBComparte db;
    private SessionManager sessionManager;
    private ReservaAdapter adapter;
    private List<Reserva> listaReservas;
    private NavController navController;

    public ListadoReservasFragment() {}

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listado_reservas, container, false);
        navController = NavHostFragment.findNavController(this);

        recyclerView = view.findViewById(R.id.recyclerReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DBComparte(requireContext());
        sessionManager = new SessionManager(requireContext());

        listaReservas = new ArrayList<>();

        adapter = new ReservaAdapter(requireContext(), listaReservas, db, reserva -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("reserva", reserva);
            navController.navigate(R.id.action_listadoReservasFragment_to_reservaPropietarioFragment, bundle);
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int idPropietario = sessionManager.getPropietarioId();
        listaReservas.clear();
        listaReservas.addAll(db.obtenerReservasPorPropietario(idPropietario));
        adapter.notifyDataSetChanged();
    }
}
