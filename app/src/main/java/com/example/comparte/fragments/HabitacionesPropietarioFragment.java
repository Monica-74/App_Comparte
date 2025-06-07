package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.adapters.ReservaAdapter;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Habitacion;
import com.example.comparte.utils.SessionManager;

import java.util.List;
/*
esta clase es para los propietarios, recordar
 */

public class HabitacionesPropietarioFragment extends Fragment {

    private RecyclerView recyclerView;
    private DBComparte db;
    private SessionManager sessionManager;

    public HabitacionesPropietarioFragment() {}

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_habitaciones_propietario, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPropietario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DBComparte(getContext());
        sessionManager = new SessionManager(getContext());

        int idPropietario = sessionManager.getPropietarioId();
        List<Habitacion> habitaciones = db.getHabitacionesPorPropietario(idPropietario);

        HabitacionAdapter adapter = new HabitacionAdapter(getContext(), habitaciones, new HabitacionAdapter.OnHabitacionActionListener() {
            @Override
            public void modificarHabitacion(Habitacion habitacion) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("habitacion", habitacion); // tu clase Habitacion debe implementar Serializable

                Navigation.findNavController(requireView())
                        .navigate(R.id.action_propietarioFragment_to_formularioFragment, bundle);

            }

            @Override
            public void eliminarHabitacion(Habitacion habitacion) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_habitacionesPropietarioFragment_to_propietarioFragment);
            }
        }, true);


        recyclerView.setAdapter(adapter);

        return view;
    }
}
