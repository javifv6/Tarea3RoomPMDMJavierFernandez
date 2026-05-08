package com.example.tarea3onlineroom3

import kotlinx.coroutines.flow.Flow

class ReservaRepository(private val reservaDao: ReservaDao) {
    val todasLasReservas: Flow<List<Reserva>> = reservaDao.obtenerTodas()
    fun insertar(reserva: Reserva) = reservaDao.insertar(reserva)
    fun actualizar(reserva: Reserva) = reservaDao.actualizar(reserva)
    fun borrar(reserva: Reserva) = reservaDao.borrar(reserva)
    fun buscarPorFechaYHora(fecha: String, hora: String): List<Reserva> = reservaDao.buscarPorFechaYHora(fecha, hora)
}