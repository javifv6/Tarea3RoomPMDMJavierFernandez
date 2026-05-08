package com.example.tarea3onlineroom3

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Clase para gestionar las herramientas de IA. Permite agregar, visualizar y eliminar herramientas
 * clasificadas por categoría, almacenándolas en Firestore.
 */class HerramientasActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var adapter: HerramientasAdapter
    
    // Categorías disponibles para clasificar las herramientas de IA
    private val categorias = arrayOf(
        "Asistentes de IA", 
        "Generación de video", 
        "Generación de imágenes", 
        "Asistentes de reuniones", 
        "Automatización", 
        "Investigación"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_herramientas)

        // Referencias a las vistas
        val etNombre = findViewById<EditText>(R.id.etNombreIA)
        val spCategoria = findViewById<Spinner>(R.id.spCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarIA)
        val rv = findViewById<RecyclerView>(R.id.rvHerramientas)

        // Configuración del Spinner con las categorías
        spCategoria.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)

        // Configuración del RecyclerView y su adaptador
        adapter = HerramientasAdapter(emptyList()) { id -> eliminarHerramienta(id) }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // Lógica para guardar una nueva herramienta
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val categoria = spCategoria.selectedItem.toString()
            val userId = auth.currentUser?.uid
            
            if (nombre.isNotEmpty() && userId != null) {
                val herramienta = HerramientaIA(nombre = nombre, categoria = categoria, userId = userId)
                // Se añade a la colección "herramientas" en la nube
                db.collection("herramientas").add(herramienta)
                etNombre.text.clear()
            } else {
                Toast.makeText(this, "Introduce un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        // Cierre de sesión y retorno a la pantalla anterior
        findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            auth.signOut()
            finish()
        }
        escucharDatos()
    }

    /**
     * Actualiza el adaptador automáticamente cada vez que los datos cambian en la nube.
     */
    private fun escucharDatos() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("herramientas")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ ->
                val lista = snapshot?.map { doc ->
                    doc.toObject(HerramientaIA::class.java).apply { id = doc.id }
                } ?: emptyList()
                adapter.actualizar(lista)
            }
    }

    /**
     * Elimina un documento específico de la colección por su ID.
     */
    private fun eliminarHerramienta(id: String) {
        db.collection("herramientas").document(id).delete()
    }
}