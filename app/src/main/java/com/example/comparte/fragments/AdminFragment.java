package com.example.comparte.fragments;
/*
 * Clase AdminFragment
 *
 * Fragmento principal destinado al rol de administrador dentro de la aplicación CompArte.
 * Este fragmento se muestra tras el inicio de sesión del usuario con rol "admin" y sirve como panel de control
 * para gestionar aspectos clave de la plataforma, como el control de usuarios, revisión de datos o tareas administrativas.
 *
 * Puede incluir botones o accesos directos a funcionalidades específicas, como visualizar estadísticas,
 * modificar datos de usuarios, eliminar cuentas o acceder a configuraciones del sistema.
 *
 * Forma parte de la estructura de navegación de la app, y se gestiona mediante el NavController desde la actividad principal.
 */


import android.content.Intent;
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

import com.example.comparte.R;
import com.example.comparte.activities.LoginActivity;
import com.example.comparte.utils.SessionManager;

public class AdminFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionManager sessionManager = new SessionManager(requireContext());
        if (!"admin".equals(sessionManager.getUserRol())) {
            Toast.makeText(getContext(), "Acceso no autorizado", Toast.LENGTH_SHORT).show();

            // Cerrar MainActivity y volver a LoginActivity
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
