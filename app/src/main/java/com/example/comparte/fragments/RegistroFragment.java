package com.example.comparte.fragments;

/*
 * Clase RegistroFragment
 *
 * Fragmento encargado de gestionar el registro de nuevos usuarios en la aplicación CompArte.
 * Presenta un formulario donde se solicitan datos como nombre, correo electrónico, contraseña y rol (inquilino, propietario o administrador).
 *
 * Este fragmento valida la información introducida, aplica cifrado a la contraseña (si corresponde),
 * y guarda los datos en la base de datos local (SQLite) a través de clases como DBComparte o DatabaseManager.
 *
 * Tras un registro exitoso, redirige al usuario al fragmento de login o a la actividad principal,
 * según la lógica definida en la aplicación.
 *
 * Su objetivo es garantizar que el proceso de alta sea seguro, accesible y adaptado a los distintos roles disponibles.
 */


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comparte.R;
import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Usuario;
import com.example.comparte.utils.CifradoContrasena;

public class RegistroFragment extends Fragment {  // Clase RegistroFragment que hereda de Fragment para mostrar las habitaciones disponibles.

    private EditText nombre, edad, email, password, genero, telefono;
    private Button btnRegistro;
    private RadioGroup radioGroupRol;
    private DBComparte dbComparte;

    public RegistroFragment() {} // Constructor vacío.

    @RequiresApi(api = Build.VERSION_CODES.O) // Necesario para hashPassword()
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // Método que se ejecuta al crear la vista del fragmento. Crea y devuelve la vista del fragmento.

        View view = inflater.inflate(R.layout.fragment_registro, container, false); // Inflar el layout del fragmento.

        // Inicializar vistas
        nombre = view.findViewById(R.id.nombre);
        edad = view.findViewById(R.id.edad);
        email = view.findViewById(R.id.emailEditText);
        password = view.findViewById(R.id.password);
        genero = view.findViewById(R.id.genero);
        telefono = view.findViewById(R.id.telefono);
        radioGroupRol = view.findViewById(R.id.radioGroupRol);
        btnRegistro = view.findViewById(R.id.btnRegistro);

        dbComparte = new DBComparte(requireContext()); // Crear una instancia de DBComparte para interactuar con la base de datos.

        btnRegistro.setOnClickListener(v -> { // Configurar el clic del botón de registro.
            String nombreText = nombre.getText().toString().trim();
            String edadText = edad.getText().toString().trim();
            String emailText = email.getText().toString().trim().toLowerCase();
            String passwordText = password.getText().toString().trim();
            String generoText = genero.getText().toString().trim();
            String telefonoText = telefono.getText().toString().trim();
            int selectedRolId = radioGroupRol.getCheckedRadioButtonId();
            String rol = (selectedRolId == R.id.radioPropietario) ? "propietario" : "inquilino"; // Obtener el rol seleccionado

            if (nombreText.isEmpty() || edadText.isEmpty() || emailText.isEmpty() ||
                    passwordText.isEmpty() || generoText.isEmpty() || telefonoText.isEmpty()) { // Validar campos obligatorios.
                Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar edad como número
            int edadInt;
            try { // Intentar convertir la edad a entero.
                edadInt = Integer.parseInt(edadText); // Convertir la edad a entero.
                if (edadInt < 65) throw new NumberFormatException(); // Si la edad es menor a 65, lanzar una excepción.
            } catch (NumberFormatException e) { // Capturar cualquier excepción que pueda ocurrir durante la conversión.
                Toast.makeText(getContext(), "Edad inválida. Tienes que ser mayor de 64 años y en números.", Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de error.
                return;
            }

            // Validar teléfono como número de 9 dígitos
            if (!telefonoText.matches("\\d{9}")) {
                Toast.makeText(getContext(), "El teléfono debe contener 9 números", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generar hash de la contraseña
            String hash = CifradoContrasena.hashPassword(passwordText);

            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreText);
            usuario.setEdad(Integer.parseInt(edadText));
            usuario.setEmail(emailText);
            usuario.setPassword(passwordText); // guardamos original también
            usuario.setContrasenaHash(hash);   // guardamos el hash
            usuario.setGenero(generoText);
            usuario.setTelefono(Integer.parseInt(telefonoText));
            usuario.setRol(rol);

            // Insertar en la base de datos
            boolean exito = dbComparte.insertarUsuario(usuario); // Insertar el usuario en la base de datos.
            if (exito) { // Si la inserción fue exitosa.
                Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de éxito.
                NavHostFragment.findNavController(RegistroFragment.this) // Navegar al fragmento de inicio de sesión.
                        .navigate(R.id.action_registroFragment_to_loginFragment); // Navegar al fragmento de inicio de sesión.
            } else {
                Toast.makeText(getContext(), "Error al registrar usuario", Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de error.
            }
        });

        return view;
    }
}
