package com.example.tarea3onlineroom3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReservaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReservaRepository
    val todasLasReservas: Flow<List<Reserva>>

    init {
        val reservaDao = ReservaDatabase.getDatabase(application).reservaDao()
        repository = ReservaRepository(reservaDao)
        todasLasReservas = repository.todasLasReservas
    }

    fun insertar(reserva: Reserva) = viewModelScope.launch(Dispatchers.IO) { repository.insertar(reserva) }
    fun actualizar(reserva: Reserva) = viewModelScope.launch(Dispatchers.IO) { repository.actualizar(reserva) }
    fun borrar(reserva: Reserva) = viewModelScope.launch(Dispatchers.IO) { repository.borrar(reserva) }
}