package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Usuario;

public class RegistroFragment extends Fragment {

    private EditText nombre,edad,email, password,genero, telefono;
    private Button btnRegistro;
    private RadioGroup radioGroupRol;
    private DBComparte dbComparte;

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
        radioGroupRol = view.findViewById(R.id.radioGroupRol);
        btnRegistro = view.findViewById(R.id.btnRegistro);

        dbComparte = new DBComparte(requireContext());


        btnRegistro.setOnClickListener(v -> {
            String nombreText = nombre.getText().toString().trim();
            String edadText = edad.getText().toString().trim();
            String emailText = email.getText().toString().trim().toLowerCase();
            String passwordText = password.getText().toString().trim();
            String generoText = genero.getText().toString().trim();
            String telefonoText = telefono.getText().toString().trim();
            int selectedRolId = radioGroupRol.getCheckedRadioButtonId();
            String rol = (selectedRolId == R.id.radioPropietario) ? "propietario" : "inquilino";

            if (nombreText.isEmpty() || edadText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || generoText.isEmpty() || telefonoText.isEmpty()) { // Validar campos obligatorios
                Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreText);
            usuario.setEdad(Integer.parseInt(edadText));
            usuario.setEmail(emailText);
            usuario.setPassword(passwordText);
            usuario.setGenero(generoText);
            usuario.setTelefono(Integer.parseInt(telefonoText));
            usuario.setRol(rol);

            // Guardar en base de datos
            boolean exito = dbComparte.insertarUsuario(usuario);
            if (exito) {
//                SessionManager sessionManager = new SessionManager(getContext());
//                sessionManager.setUserRol(rol);

                Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(RegistroFragment.this)
                        .navigate(R.id.action_registroFragment_to_loginFragment);

            } else {
                Toast.makeText(getContext(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



}



