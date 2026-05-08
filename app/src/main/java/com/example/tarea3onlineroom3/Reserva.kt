package com.example.tarea3onlineroom3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_reservas")
data class Reserva(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreJinete: String,
    val movil: String,
    val nombreCaballo: String,
    val fecha: String, // Formato yyyy-MM-dd para ordenar correctamente
    val hora: String,  // Solo 10:00 o 11:00
    val comentario: String
)