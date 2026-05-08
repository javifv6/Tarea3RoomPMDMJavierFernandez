package com.example.tarea3onlineroom3

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.util.Calendar

class Main1Activity : AppCompatActivity() {

    private val viewModel: ReservaViewModel by viewModels()
    private lateinit var adapter: ReservaAdapter
    private var fechaSeleccionada = ""
    private var horaSeleccionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        val etJinete = findViewById<EditText>(R.id.etJinete)
        val etMovil = findViewById<EditText>(R.id.etMovil)
        val etCaballo = findViewById<EditText>(R.id.etCaballo)
        val etComentario = findViewById<EditText>(R.id.etComentario)
        val btnFecha = findViewById<Button>(R.id.btnFecha)
        val btnHora = findViewById<Button>(R.id.btnHora)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnAtras = findViewById<Button>(R.id.btnAtras)
        btnAtras.setOnClickListener { finish() } // Cierra esta pantalla y vuelve a la anterior

        adapter = ReservaAdapter(emptyList()) { reserva -> viewModel.borrar(reserva) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observar base de datos
        lifecycleScope.launch {
            viewModel.todasLasReservas.collect { reservas ->
                adapter.actualizarLista(reservas)
            }
        }

        btnFecha.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                fechaSeleccionada = "$day/${month + 1}/$year"
                btnFecha.text = fechaSeleccionada
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnHora.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(this, { _, hour, minute ->
                horaSeleccionada = String.format("%02d:%02d", hour, minute)
                btnHora.text = horaSeleccionada
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }

        btnGuardar.setOnClickListener {
            val movil = etMovil.text.toString()
            val caballo = etCaballo.text.toString()
            if (etJinete.text.isNotEmpty() && movil.isNotEmpty() && fechaSeleccionada.isNotEmpty() && horaSeleccionada.isNotEmpty()) {
                val reserva = Reserva(
                    nombreJinete = etJinete.text.toString(),
                    movil = movil,
                    nombreCaballo = caballo,
                    fecha = fechaSeleccionada,
                    hora = horaSeleccionada,
                    comentario = etComentario.text.toString()
                )
                viewModel.insertar(reserva)
                enviarWhatsApp(movil, caballo, fechaSeleccionada, horaSeleccionada)
            } else {
                Toast.makeText(this, "Rellena los datos básicos, fecha y hora", Toast.LENGTH_SHORT).show()
            }
        }

        btnBuscar.setOnClickListener {
            if (fechaSeleccionada.isNotEmpty() && horaSeleccionada.isNotEmpty()) {
                lifecycleScope.launch(kotlinx.coroutines.Dispatchers.IO) {
                    val dao = ReservaDatabase.getDatabase(applicationContext).reservaDao()
                    val resultado = dao.buscarPorFechaYHora(fechaSeleccionada, horaSeleccionada)
                    runOnUiThread { adapter.actualizarLista(resultado) }
                }
            } else {
                Toast.makeText(this, "Selecciona fecha y hora para buscar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarWhatsApp(movil: String, caballo: String, fecha: String, hora: String) {
        val mensaje = "Hola, tu reserva para el paseo con el caballo $caballo el día $fecha a las $hora está confirmada."
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$movil&text=${Uri.encode(mensaje)}")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
        }
    }
}