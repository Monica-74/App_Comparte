package com.example.comparte.entities;

/**
 * Enum EstadoReserva
 *
 * Define los distintos estados posibles que puede tener una reserva dentro de la aplicación CompArte.
 * Este enum mejora la claridad y robustez del código al evitar el uso de cadenas de texto o valores numéricos
 * para representar estados, reduciendo así el riesgo de errores.
 *
 * Los valores definidos son:
 * - PENDIENTE: La reserva ha sido solicitada, pero aún no ha sido confirmada ni rechazada.
 * - CONFIRMADA: La reserva ha sido aceptada por el propietario.
 * - RECHAZADA: La reserva ha sido denegada o cancelada.
 *
 * Este enum se utiliza para controlar el flujo del ciclo de vida de una reserva, tanto en la base de datos
 * como en la lógica de la interfaz de usuario.
 */

public enum EstadoReserva {
    PENDIENTE,
    CONFIRMADA,
    RECHAZADA
}
