package com.example.comparte.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import org.junit.Test;
import static org.junit.Assert.*;

/*
Prueba unitaria de la clase CifradoContrasena.
 *
 * Objetivo:
 * Verificar que el metodo hashPassword(String password)(para cifrar contraseñas) funcione correctamente.
 *
 * ¿Qué se comprueba?
 * Que el metodo no devuleva null al generar el hash de una contraseña.
 * Que si pasamos la misma contraseña dos veces, el hash generado sea igual.
 * Que contraseñas diferentes generen hashes distintos.
 */

public class CifradoContrasenaTest {

    @SuppressLint("UseSdkSuppress")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void testHashPassword() {
        String password1 = "1234";
        String password2 = "5678";

        String hash1 = CifradoContrasena.hashPassword(password1);
        String hash2 = CifradoContrasena.hashPassword(password1);
        String hash3 = CifradoContrasena.hashPassword(password2);

        assertNotNull(hash1);
        assertEquals(hash1, hash2); // misma contraseña, mismo hash
        assertNotEquals(hash1, hash3); // contraseñas distintas, hashes distintos
    }
}
