package com.example.tarea3onlineroom3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HerramientasAdapter(
    private var lista: List<HerramientaIA>,
    private val onEliminar: (String) -> Unit
) : RecyclerView.Adapter<HerramientasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreIA)
        val categoria: TextView = view.findViewById(R.id.tvCategoriaIA)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarIA)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_herramienta, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.nombre.text = item.nombre
        holder.categoria.text = item.categoria
        holder.btnEliminar.setOnClickListener { onEliminar(item.id) }
    }

    override fun getItemCount() = lista.size

    fun actualizar(nuevaLista: List<HerramientaIA>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}