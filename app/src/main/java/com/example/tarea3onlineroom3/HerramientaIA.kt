package com.example.tarea3onlineroom3

/**
 * Clase de datos que representa una herramienta de IA para ser almacenada en Firebase.
 * Contiene campos para el ID del documento, el nombre de la IA, su categoría y el ID del usuario propietario.
 */
data class HerramientaIA(
    /** ID del documento en Firestore (se asigna dinámicamente) */
    var id: String = "",
    
    /** Nombre de la herramienta de IA */
    val nombre: String = "",
    
    /** Categoría a la que pertenece (p.ej., Generación de video) */
    val categoria: String = "",
    
    /** ID del usuario que registró esta herramienta (para filtrado) */
    val userId: String = ""
)