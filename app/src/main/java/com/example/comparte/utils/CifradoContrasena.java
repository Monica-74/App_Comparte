package com.example.comparte.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CifradoContrasena {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // ✅ Versión simplificada usando una sal fija interna
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String hashPassword(String password) {
        try {
            // SAL FIJA — solo para pruebas o proyectos académicos
            byte[] fixedSalt = "salFijaProyectoComparte".getBytes();

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), fixedSalt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}
