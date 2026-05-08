package com.example.tarea3onlineroom3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el RecyclerView que muestra la lista de reservas.
 */
class ReservaAdapter(
    private var reservas: List<Reserva>,
    private val onBorrarClick: (Reserva) -> Unit,
    private val onEditarClick: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {

    /**
     * ViewHolder que mantiene las referencias a las vistas de cada elemento de la lista.
     */
    class ReservaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvJineteCaballo: TextView = view.findViewById(R.id.tvJineteCaballo)
        val btnBorrar: Button = view.findViewById(R.id.btnBorrar)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
    }

    /** Crea nuevas vistas (invocado por el layout manager) */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return ReservaViewHolder(view)
    }

    /** Reemplaza el contenido de una vista (invocado por el layout manager) */
    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservas[position]
        holder.tvFechaHora.text = "${reserva.fecha} - ${reserva.hora}"
        holder.tvJineteCaballo.text = "Jinete: ${reserva.nombreJinete} | Caballo: ${reserva.nombreCaballo}"

        // Configura los listeners para las acciones de borrar y editar
        holder.btnBorrar.setOnClickListener { onBorrarClick(reserva) }
        holder.btnEditar.setOnClickListener { onEditarClick(reserva) }
    }

    /** Devuelve el tamaño de la lista de datos */
    override fun getItemCount() = reservas.size

    /** 
     * Actualiza la lista interna de reservas y notifica al RecyclerView para redibujarse.
     */
    fun actualizarLista(nuevaLista: List<Reserva>) {
        reservas = nuevaLista
        notifyDataSetChanged()
    }
}