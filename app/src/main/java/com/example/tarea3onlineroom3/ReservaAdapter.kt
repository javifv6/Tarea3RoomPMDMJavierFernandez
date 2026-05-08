package com.example.tarea3onlineroom3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReservaAdapter(
    private var reservas: List<Reserva>,
    private val onBorrarClick: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvJineteCaballo: TextView = view.findViewById(R.id.tvJineteCaballo)
        val btnBorrar: Button = view.findViewById(R.id.btnBorrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return ReservaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservas[position]
        holder.tvFechaHora.text = "${reserva.fecha} - ${reserva.hora}"
        holder.tvJineteCaballo.text = "Jinete: ${reserva.nombreJinete} | Caballo: ${reserva.nombreCaballo}"
        holder.btnBorrar.setOnClickListener { onBorrarClick(reserva) }
    }

    override fun getItemCount() = reservas.size

    fun actualizarLista(nuevaLista: List<Reserva>) {
        reservas = nuevaLista
        notifyDataSetChanged()
    }
}