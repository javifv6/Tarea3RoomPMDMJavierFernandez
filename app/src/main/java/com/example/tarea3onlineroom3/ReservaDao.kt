package com.example.tarea3onlineroom3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para la entidad Reserva.
 * Define las operaciones permitidas para interactuar con los datos de la tabla.
 */
@Dao
interface ReservaDao {

    /** Inserta una nueva reserva en la base de datos */
    @Insert
    fun insertar(reserva: Reserva)

    /** Actualiza una reserva existente */
    @Update
    fun actualizar(reserva: Reserva)

    /** Elimina una reserva de la base de datos */
    @Delete
    fun borrar(reserva: Reserva)

    /**
     * Obtiene todas las reservas ordenadas por fecha y hora de forma descendente.
     */
    @Query("SELECT * FROM tabla_reservas ORDER BY fecha DESC, hora DESC")
    fun obtenerTodas(): Flow<List<Reserva>>

    /**
     * Busca reservas que coincidan exactamente con una fecha y hora específicas.
     */
    @Query("SELECT * FROM tabla_reservas WHERE fecha = :fechaBuscada AND hora = :horaBuscada ORDER BY fecha DESC, hora DESC")
    fun buscarPorFechaYHora(fechaBuscada: String, horaBuscada: String): List<Reserva>
}