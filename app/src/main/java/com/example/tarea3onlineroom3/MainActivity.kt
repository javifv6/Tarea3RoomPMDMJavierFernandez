package com.example.tarea3onlineroom3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1)
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)

        btnEjercicio1.setOnClickListener {
            startActivity(Intent(this, Main1Activity::class.java))
        }

        btnEjercicio2.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
        }
    }
}