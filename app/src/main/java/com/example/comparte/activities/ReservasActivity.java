//package com.example.comparte.activities;
//
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.example.comparte.R;
//
//
//public class ReservasActivity extends AppCompatActivity {
//
//
//    private EditText nombreReserva, descripcionReserva, fechaReserva, telefonoReserva, emailReserva;
//
//
//
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_reserva);
//        nombreReserva = findViewById(R.id.nombreReserva);
//        descripcionReserva = findViewById(R.id.descripcionReserva);
//        fechaReserva = findViewById(R.id.fechaReserva);
//        telefonoReserva = findViewById(R.id.telefonoReserva);
//        emailReserva = findViewById(R.id.emailReserva);
//        Button btnConfirmarReserva = findViewById(R.id.btnConfirmarReserva);
//
//
//        btnConfirmarReserva.setOnClickListener(v -> {
//            String nombre = nombreReserva.getText().toString().trim();
//            String descripcion = descripcionReserva.getText().toString().trim();
//            String fecha = fechaReserva.getText().toString().trim();
//            String telefono = telefonoReserva.getText().toString().trim();
//            String email = emailReserva.getText().toString().trim();
//
//
//            if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
//                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Reserva confirmada", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }
//
//
//}
