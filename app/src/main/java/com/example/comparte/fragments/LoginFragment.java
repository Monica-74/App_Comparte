package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.comparte.R;
import com.example.comparte.activities.AdminActivity;
import com.example.comparte.activities.MainActivity;
import com.example.comparte.controllers.LoginController;

public class LoginFragment extends Fragment {

    private LoginController loginController;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private Button btnRegistroNuevo;

    public LoginFragment() {
    }

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflar el layout correspondiente al login
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inicializar vistas desde la vista inflada
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        btnRegistroNuevo = view.findViewById(R.id.btnRegistroNuevo);

        // Inicializar el controlador
        loginController = new LoginController(getContext());

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, introduce el correo electrónico y la contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            FragmentActivity activityForNavigation = getActivity();// Obtenemos la actividad actual
            if (activityForNavigation == null) {
                Toast.makeText(getContext(), "Error: La actividad no está disponible para la navegación.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loginController.esAdministrador(email, password)) {
                Toast.makeText(getContext(), "Bienvenido administrador a CompArte", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), AdminActivity.class));
            } else if (loginController.login(email, password)) {
                Toast.makeText(getContext(), "Inicio de sesión válido", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            } else {
                Toast.makeText(getContext(), "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistroNuevo.setOnClickListener(v -> { // Añadir un OnClickListener al botón de registro
            try {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registroFragment);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error al navegar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
