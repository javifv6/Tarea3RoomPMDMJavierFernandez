package com.example.tarea3onlineroom3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad Principal que sirve como menú de entrada a la aplicación.
 * Permite navegar entre el Ejercicio 1 (Hípica) y el Ejercicio 2 (Firebase).
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los botones del menú
        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1)
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)

        // Navegación a la gestión de reservas (Room)
        btnEjercicio1.setOnClickListener {
            startActivity(Intent(this, Main1Activity::class.java))
        }

        // Navegación a la autenticación (Firebase)
        btnEjercicio2.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
        }
    }
}