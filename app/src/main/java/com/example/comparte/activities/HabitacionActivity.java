package com.example.comparte.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comparte.R;
import com.example.comparte.adapters.HabitacionAdapter;
import com.example.comparte.models.Habitacion;

import java.util.List;

public class HabitacionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HabitacionAdapter habitacionAdapter;
    private List<Habitacion> listaHabitaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_habitacion);
        recyclerView = findViewById(R.id.recyclerHabitaciones);
        habitacionAdapter = new HabitacionAdapter(this, listaHabitaciones);
        recyclerView.setAdapter(habitacionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = new Intent(this, HabitacionActivity.class);
        startActivity(intent);

    }



}
