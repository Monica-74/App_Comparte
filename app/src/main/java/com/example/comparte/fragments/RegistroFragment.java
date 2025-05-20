package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.example.comparte.controllers.LoginController;
import com.example.comparte.R;

public class RegistroFragment extends Fragment {

    private EditText nombre;
    private EditText edad;
    private EditText email;
    private EditText password;
    private EditText genero;
    private EditText telefono;
    private Button btnRegistro;

    public RegistroFragment() {
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registro, container, false); // Inflar el layout del fragmento

        //inicializo las vistas
        nombre = view.findViewById(R.id.nombre);
        edad = view.findViewById(R.id.edad);
        email = view.findViewById(R.id.emailEditText);
        password = view.findViewById(R.id.password);
        genero = view.findViewById(R.id.genero);
        telefono = view.findViewById(R.id.telefono);
        btnRegistro = view.findViewById(R.id.btnRegistro);

        if (nombre != null && edad != null && email != null && password != null && genero != null && telefono != null && btnRegistro != null) {
            Toast.makeText(getContext(), "Error al inicializar las vistas", Toast.LENGTH_SHORT).show();
            return view;
        }

        if (email == null || password == null || btnRegistro == null ) { // Añado la comprobación para textViewIrARegistro
            Toast.makeText(getContext(), "Error al inicializar la interfaz de login.", Toast.LENGTH_LONG).show();
            return view;
        }

//        FragmentActivity currentActivity = getActivity(); // Obtenemos la actividad actual
//        if (currentActivity == null) {
//            Toast.makeText(getContext(), "Error crítico: El fragmento no está asociado a una actividad.", Toast.LENGTH_LONG).show();
//            return view;
//        }
        //registroController = new RegistroController(getContext(); //descomentar cdo exista


        btnRegistro.setOnClickListener(v -> {
            String nombreText = nombre.getText().toString();
            String edadText = edad.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            String generoText = genero.getText().toString();
            String telefonoText = telefono.getText().toString();

        });
        return view;
    }


}



