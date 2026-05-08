package com.example.tarea3onlineroom3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el RecyclerView que muestra las herramientas de IA desde Firebase Firestore.
 */
class HerramientasAdapter(
    private var lista: List<HerramientaIA>,
    private val onEliminar: (String) -> Unit
) : RecyclerView.Adapter<HerramientasAdapter.ViewHolder>() {

    /**
     * ViewHolder que mantiene las referencias a las vistas de cada tarjeta de herramienta.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreIA)
        val categoria: TextView = view.findViewById(R.id.tvCategoriaIA)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarIA)
    }

    /** Crea la vista para cada elemento */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_herramienta, parent, false))

    /** Vincula los datos de la herramienta con las vistas del ViewHolder */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.nombre.text = item.nombre
        holder.categoria.text = item.categoria
        
        // Listener para eliminar la herramienta por su ID de Firestore
        holder.btnEliminar.setOnClickListener { onEliminar(item.id) }
    }

    /** Devuelve la cantidad de elementos en la lista */
    override fun getItemCount() = lista.size

    /** 
     * Actualiza la lista interna y refresca el RecyclerView.
     */
    fun actualizar(nuevaLista: List<HerramientaIA>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}