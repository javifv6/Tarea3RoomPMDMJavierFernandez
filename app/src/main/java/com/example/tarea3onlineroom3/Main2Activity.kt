package com.example.tarea3onlineroom3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Actividad para la gestión de autenticación con Firebase (Ejercicio 2).
 * Permite a los usuarios registrarse e iniciar sesión con correo y contraseña.
 * Tras un acceso exitoso, redirige a la gestión de herramientas ([HerramientasActivity]).
 */
class Main2Activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Inicializamos Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Referencias a los componentes de la interfaz
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val btnAtras = findViewById<Button>(R.id.btnAtrasFirebase)

        // Lógica de Inicio de Sesión
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "¡Login correcto!", Toast.LENGTH_SHORT).show()
                            // Redirección a la pantalla de gestión de IA
                            startActivity(Intent(this, HerramientasActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Rellena email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Lógica de Registro de nuevo usuario
        btnRegistro.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "¡Usuario registrado!", Toast.LENGTH_SHORT).show()
                            // Redirección a la pantalla de gestión de IA tras el registro
                            startActivity(Intent(this, HerramientasActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Rellena email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para volver al menú principal
        btnAtras.setOnClickListener { finish() }
    }
}