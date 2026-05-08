package com.example.tarea3onlineroom3

import kotlinx.coroutines.flow.Flow

/**
 * Clase Repositorio que actúa como mediadora entre el DAO y el ViewModel.
 */
class ReservaRepository(private val reservaDao: ReservaDao) {

    /** 
     * Flujo con todas las reservas. 
     * Se actualiza automáticamente cuando cambian los datos en la base de datos.
     */
    val todasLasReservas: Flow<List<Reserva>> = reservaDao.obtenerTodas()

    /** Inserta una reserva a través del DAO */
    fun insertar(reserva: Reserva) {
        reservaDao.insertar(reserva)
    }

    /** Actualiza una reserva a través del DAO */
    fun actualizar(reserva: Reserva) {
        reservaDao.actualizar(reserva)
    }

    /** Borra una reserva a través del DAO */
    fun borrar(reserva: Reserva) {
        reservaDao.borrar(reserva)
    }

    /** Busca reservas por fecha y hora a través del DAO */
    fun buscarPorFechaYHora(fecha: String, hora: String): List<Reserva> {
        return reservaDao.buscarPorFechaYHora(fecha, hora)
    }
}