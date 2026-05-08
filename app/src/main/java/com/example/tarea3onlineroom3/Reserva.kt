package com.example.tarea3onlineroom3

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Clase de datos que representa una entidad "Reserva" en la base de datos Room.
 * Cada instancia de esta clase corresponde a una fila en la tabla "tabla_reservas".
 */
@Entity(tableName = "tabla_reservas")
data class Reserva(
    /** Identificador único para cada reserva. Se genera automáticamente. */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** Nombre del usuario que realiza la reserva */
    val nombreJinete: String,

    /** Número de teléfono móvil para contacto y confirmación por WhatsApp */
    val movil: String,

    /** Nombre del caballo asignado o solicitado */
    val nombreCaballo: String,

    /** Fecha de la reserva (Formato yyyy-MM-dd para ordenar correctamente) */
    val fecha: String,

    /** Hora de la reserva (Solo 10:00 o 11:00) */
    val hora: String,

    /** Comentario adicional sobre la reserva */
    val comentario: String
)