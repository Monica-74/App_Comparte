package com.example.comparte.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comparte.R;
import com.example.comparte.activities.MainActivity;
import com.example.comparte.controller.LoginController;
import com.example.comparte.entities.Usuario;
import com.example.comparte.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

public class LoginFragment extends Fragment {

    private LoginController loginController;
    private EditText emailEditText, passwordEditText;
    private SessionManager sessionManager;
    private MaterialButton btnLoginIniciar;

    public LoginFragment() {
    }

    @SuppressLint({"MissingInflatedId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflar el layout correspondiente al login
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Animacion del logo
        ImageView logo = view.findViewById(R.id.logoComparte);
        Animation animacionLogo = AnimationUtils.loadAnimation(getContext(), R.anim.logo_animado_lanzado);
        logo.startAnimation(animacionLogo);


        // Inicializar vistas desde la vista inflada
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        btnLoginIniciar = view.findViewById(R.id.btnLoginIniciar);
        TextView btnRegistroNuevo = view.findViewById(R.id.btnRegistroNuevo);

        // Inicializar el controlador
        loginController = new LoginController(getContext());
        sessionManager = new SessionManager(getContext());

        btnLoginIniciar.setOnClickListener(v -> {
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

            //sessionManager = new SessionManager(getContext());

            if (loginController.esAdministrador(email, password)) { // Si es administrador
                sessionManager.setUserRol("admin");

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("rol", "admin");
                startActivity(intent);
                requireActivity().finish();
            } else {
                //Usuario usuario = loginController.login(email, password);
                Usuario usuario = loginController.loginYObtenerUsuario(email, password);


                if (usuario != null) {
                    String rol = usuario.getRol().toLowerCase().trim();
                    sessionManager.setUserRol(rol);
                    sessionManager.saveUserId(usuario.getId_usuario());

                    // Si el usuario es propietario, guardar id_propietario
                    if ("propietario".equals(rol)) {
                        int idPropietario = loginController.obtenerIdPropietarioPorUsuario(usuario.getId_usuario());
                        sessionManager.guardarPropietarioId(idPropietario);

                        Log.d("DEBUG", "ID del propietario guardado en sesión: " + idPropietario);
                        Toast.makeText(getContext(), "Propietario ID: " + idPropietario, Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("rol", "propietario");
                        startActivity(intent);
                        requireActivity().finish();

                    } else if ("inquilino".equals(rol)) {

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("rol", "inquilino");
                        startActivity(intent);
                        requireActivity().finish();

                    } else {
                        Toast.makeText(getContext(), "Rol no reconocido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistroNuevo.setOnClickListener(view1 -> { // Añadir un OnClickListener al botón de registro
            try {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registroFragment);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error al navegar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}





