package com.example.tarea3onlineroom3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel que gestiona los datos de la UI para las Reservas.
 * Actúa como puente entre la actividad (Main1Activity) y el repositorio.
 * Mantiene los datos ante cambios de configuración (como rotación de pantalla).
 */
class ReservaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReservaRepository
    
    /** 
     * Flujo de datos observado por la Activity para actualizar el RecyclerView en tiempo real.
     */
    val todasLasReservas: Flow<List<Reserva>>

    init {
        // Inicialización de la DB, el DAO y el repositorio
        val reservaDao = ReservaDatabase.getDatabase(application).reservaDao()
        repository = ReservaRepository(reservaDao)
        todasLasReservas = repository.todasLasReservas
    }

    /**
     * Lanza una corrutina en el hilo de IO para insertar una reserva.
     */
    fun insertar(reserva: Reserva) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertar(reserva)
    }

    /**
     * Lanza una corrutina en el hilo de IO para actualizar una reserva.
     */
    fun actualizar(reserva: Reserva) = viewModelScope.launch(Dispatchers.IO) {
        repository.actualizar(reserva)
    }

    /**
     * Lanza una corrutina en el hilo de IO para borrar una reserva.
     */
    fun borrar(reserva: Reserva) = viewModelScope.launch(Dispatchers.IO) {
        repository.borrar(reserva)
    }

    /**
     * Busca reservas por fecha y hora de forma asíncrona.
     * Cambia al hilo de IO para la consulta y devuelve el resultado a través de un callback en el hilo principal.
     */
    fun buscarPorFechaYHora(
        fecha: String,
        hora: String,
        onResultado: (List<Reserva>) -> Unit
    ) {
        viewModelScope.launch {
            val resultado = withContext(Dispatchers.IO) {
                repository.buscarPorFechaYHora(fecha, hora)
            }
            onResultado(resultado)
        }
    }
}