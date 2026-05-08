package com.example.tarea3onlineroom3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {

    @Insert
    fun insertar(reserva: Reserva)

    @Update
    fun actualizar(reserva: Reserva)

    @Delete
    fun borrar(reserva: Reserva)

    @Query("SELECT * FROM tabla_reservas ORDER BY fecha DESC, hora DESC")
    fun obtenerTodas(): Flow<List<Reserva>>

    @Query("SELECT * FROM tabla_reservas WHERE fecha = :fechaBuscada AND hora = :horaBuscada ORDER BY fecha DESC, hora DESC")
    fun buscarPorFechaYHora(fechaBuscada: String, horaBuscada: String): List<Reserva>
}