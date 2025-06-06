package com.example.comparte.activities;




import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.comparte.R;


public class AdminActivity extends AppCompatActivity {


    Button btnPublicidad, btnUsuarios, btnContenidos, btnReservas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);


        // Asociamos los botones con sus ids para poder acceder a ellos
        btnPublicidad = findButtonByPosition(0);
        btnUsuarios = findButtonByPosition(1);
        btnContenidos = findButtonByPosition(2);
        btnReservas = findButtonByPosition(3);


        // Configuramos los eventos: click en los botones:publicidad,usuarios,contenido,reservas.
        if (btnPublicidad != null) {
            btnPublicidad.setOnClickListener(view -> {
                Toast.makeText(AdminActivity.this, "no tiene funcionalidad, queda como mejora", Toast.LENGTH_SHORT).show();


            });
        } else {
            Log.e("AdminActivity", "btnPublicidad es null");
        }


        if (btnUsuarios != null) {
            btnUsuarios.setOnClickListener(view -> {
                Toast.makeText(AdminActivity.this, "no tiene funcionalidad, queda como mejora", Toast.LENGTH_SHORT).show();
            });
        } else {
            Log.e("AdminActivity", "btnUsuarios es null");


        }


        if (btnContenidos != null) {
            btnContenidos.setOnClickListener(view -> {
                Toast.makeText(AdminActivity.this, "no tiene funcionalidad, queda como mejora", Toast.LENGTH_SHORT).show();
            });
        } else {
            Log.e("AdminActivity", "btnContenidos es null");
        }


       /*
       este es el único botón que va a tener funcionalidad en admin para que se pueda apreciar la funcionalidad, creo que reservas
       es la action que tiene más interés para el admin y para el proyecto.
        */
        if (btnReservas != null) {
            btnReservas.setOnClickListener(view -> {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                intent.putExtra("fragmento_destino", "reservas");
                startActivity(intent);
                finish(); // opcional: cerrar AdminActivity para no volver atrás
            });
        }
    }


    // Método auxiliar para encontrar el botón dentro de cada LinearLayout del GridLayout
    private Button findButtonByPosition(int position) {
        int[] buttonIds = {
                R.id.btnPublicidad,
                R.id.btnUsuarios,
                R.id.btnContenido,
                R.id.btnReservas,


        };
        return findViewById(buttonIds[position]);
    }
}


