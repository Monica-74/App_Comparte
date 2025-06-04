package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.utils.SessionManager;

import java.util.List;

public class HabitacionFragment extends Fragment {

    private RecyclerView recyclerHabitaciones;
    private View layoutSinAnuncios;
    private DBComparte dbComparte;
    private SessionManager sessionManager;
    private HabitacionAdapter adapter;

    public HabitacionFragment() {}

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflar layout que contiene el RecyclerView y el layout vacío
        View view = inflater.inflate(R.layout.fragment_habitacion, container, false);

        recyclerHabitaciones = view.findViewById(R.id.recyclerHabitaciones);
        layoutSinAnuncios = view.findViewById(R.id.layoutSinAnuncios);
        recyclerHabitaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        dbComparte = new DBComparte(getContext());
        sessionManager = new SessionManager(getContext());

        int idPropietario = sessionManager.getPropietarioId();
        List<Habitacion> habitaciones = dbComparte.getHabitacionesPorPropietario(idPropietario);

        // Verificar si hay habitaciones
        if (habitaciones.isEmpty()) {
            layoutSinAnuncios.setVisibility(View.VISIBLE);
            recyclerHabitaciones.setVisibility(View.GONE);
        } else {
            layoutSinAnuncios.setVisibility(View.GONE);
            recyclerHabitaciones.setVisibility(View.VISIBLE);

            adapter = new HabitacionAdapter(getContext(), habitaciones, new HabitacionAdapter.OnHabitacionActionListener() {
                @Override
                public void modificarHabitacion(Habitacion habitacion) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("habitacion", habitacion);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.formularioFragment, bundle);
                }

                @Override
                public void eliminarHabitacion(Habitacion habitacion) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirmar eliminación")
                            .setMessage("¿Estás segur@ de que quieres eliminar este anuncio?")
                            .setPositiveButton("Eliminar", (dialog, which) -> {
                                dbComparte.eliminarHabitacion(habitacion.getId());
                                habitaciones.remove(habitacion);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Anuncio eliminado: " + habitacion.getTitulo(), Toast.LENGTH_SHORT).show();

                                if (habitaciones.isEmpty()) {
                                    layoutSinAnuncios.setVisibility(View.VISIBLE);
                                    recyclerHabitaciones.setVisibility(View.GONE);
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
            });

            recyclerHabitaciones.setAdapter(adapter);
        }

        // Acción del botón "Crear anuncio" en layout vacío
        View btnCrear = view.findViewById(R.id.btnCrearAnuncio);
        if (btnCrear != null) {
            btnCrear.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.formularioFragment);
            });
        }

        return view;
    }
}
