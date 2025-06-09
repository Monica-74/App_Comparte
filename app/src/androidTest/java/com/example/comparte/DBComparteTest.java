package com.example.comparte;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.comparte.database.DBComparte;
import com.example.comparte.entities.Reserva;

import org.junit.Test;
import org.junit.runner.RunWith;
/*
 * Prueba de rendimiento sobre el método insertarReserva() de la clase DBComparte.
 *
 * Objetivo:
 * Medir el tiempo que tarda el programa en insertar 100 reservas en la base de datos SQLite,
 * simulando que 100 inquilinos quieren reservar una habitación.
 *
 * Justificación:
 * Se ha realizado para evaluar la eficiencia del método de inserción de reservas,
 * considerando que es una funcionalidad central en la app "Comparte" para el rol de inquilino.
 *
 * Consideraciones:
 * se asume que existen un inquilino y una habitación, válidos con id 1 en la base de datos local.
 */


@RunWith(AndroidJUnit4.class)
public class DBComparteTest {

    @Test
    public void pruebaReserva() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBComparte db = new DBComparte(context);

        long start = System.currentTimeMillis(); // tiempo de inicio

        for (int i = 0; i < 100; i++) {
            Reserva reserva = new Reserva();
            reserva.setNombreInquilino("Inquilino " + i);
            reserva.setDescripcionHabitacion("Descripción prueba " + i);
            reserva.setFechaReserva("2025-06-09");
            reserva.setTelefonoInquilino("60012345" + i);
            reserva.setEmailInquilino("inquilino" + i + "@mail.com");
            reserva.setIdInquilino(1);     // Usa un ID válido de prueba
            reserva.setIdHabitacion(1);    // Usa un ID válido de prueba
            reserva.setFechaInicio("2025-06-10");
            reserva.setFechaFin("2025-06-15");
            reserva.setEstado(com.example.comparte.entities.EstadoReserva.PENDIENTE);

            db.insertarReserva(reserva);
        }

        long end = System.currentTimeMillis();// para obtener el tiempo de ejecución total del proceso.

        System.out.println("Tiempo total para insertar 100 reservas: " + (end - start) + " ms");
    }
}
