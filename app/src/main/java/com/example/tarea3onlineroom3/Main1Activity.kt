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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Main1Activity : AppCompatActivity() {

    private val viewModel: ReservaViewModel by viewModels()

    private lateinit var adapter: ReservaAdapter

    private lateinit var etJinete: EditText
    private lateinit var etMovil: EditText
    private lateinit var etCaballo: EditText
    private lateinit var etComentario: EditText

    private lateinit var btnFecha: Button
    private lateinit var btnHora: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnMostrarTodas: Button
    private lateinit var btnAtras: Button

    private var fechaSeleccionada = ""
    private var horaSeleccionada = ""

    private var reservaEnEdicion: Reserva? = null

    private val formatoBD = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val formatoMostrar = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        inicializarVistas()
        configurarRecyclerView()
        observarReservas()
        configurarEventos()
    }

    private fun inicializarVistas() {

        etJinete = findViewById(R.id.etJinete)
        etMovil = findViewById(R.id.etMovil)
        etCaballo = findViewById(R.id.etCaballo)
        etComentario = findViewById(R.id.etComentario)

        btnFecha = findViewById(R.id.btnFecha)
        btnHora = findViewById(R.id.btnHora)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnMostrarTodas = findViewById(R.id.btnMostrarTodas)
        btnAtras = findViewById(R.id.btnAtras)
    }

    private fun configurarRecyclerView() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ReservaAdapter(
            emptyList(),

            onBorrarClick = { reserva ->

                AlertDialog.Builder(this)
                    .setTitle("Eliminar reserva")
                    .setMessage("¿Seguro que quieres eliminar esta reserva?")
                    .setPositiveButton("Sí") { _, _ ->

                        viewModel.borrar(reserva)

                        Toast.makeText(
                            this,
                            "Reserva eliminada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
            },

            onEditarClick = { reserva ->

                reservaEnEdicion = reserva

                etJinete.setText(reserva.nombreJinete)
                etMovil.setText(reserva.movil)
                etCaballo.setText(reserva.nombreCaballo)
                etComentario.setText(reserva.comentario)

                fechaSeleccionada = reserva.fecha
                horaSeleccionada = reserva.hora

                btnFecha.text = convertirFechaParaMostrar(reserva.fecha)
                btnHora.text = reserva.hora

                btnGuardar.text = "Actualizar reserva"
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observarReservas() {

        lifecycleScope.launch {

            viewModel.todasLasReservas.collect { reservas ->

                adapter.actualizarLista(reservas)
            }
        }
    }

    private fun configurarEventos() {

        btnFecha.setOnClickListener {
            mostrarSelectorFecha()
        }

        btnHora.setOnClickListener {
            mostrarSelectorHora()
        }

        btnGuardar.setOnClickListener {
            guardarReserva()
        }

        btnBuscar.setOnClickListener {
            buscarReservas()
        }

        btnMostrarTodas.setOnClickListener {

            lifecycleScope.launch {

                viewModel.todasLasReservas.collect { reservas ->

                    adapter.actualizarLista(reservas)
                }
            }
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun mostrarSelectorFecha() {

        val calendario = Calendar.getInstance()

        DatePickerDialog(
            this,

            { _, year, month, dayOfMonth ->

                val fecha = Calendar.getInstance()

                fecha.set(year, month, dayOfMonth)

                val diaSemana = fecha.get(Calendar.DAY_OF_WEEK)

                if (diaSemana != Calendar.SATURDAY &&
                    diaSemana != Calendar.SUNDAY
                ) {

                    Toast.makeText(
                        this,
                        "Solo se permiten reservas en sábado o domingo",
                        Toast.LENGTH_LONG
                    ).show()

                    return@DatePickerDialog
                }

                fechaSeleccionada = formatoBD.format(fecha.time)

                btnFecha.text =
                    formatoMostrar.format(fecha.time)
            },

            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)

        ).show()
    }

    private fun mostrarSelectorHora() {

        TimePickerDialog(
            this,

            { _, hourOfDay, minute ->

                if ((hourOfDay == 10 || hourOfDay == 11)
                    && minute == 0
                ) {

                    horaSeleccionada =
                        String.format("%02d:%02d", hourOfDay, minute)

                    btnHora.text = horaSeleccionada

                } else {

                    Toast.makeText(
                        this,
                        "Solo se permiten paseos a las 10:00 o 11:00",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },

            10,
            0,
            true

        ).show()
    }

    private fun guardarReserva() {

        val jinete =
            etJinete.text.toString().trim()

        val movil =
            etMovil.text.toString().trim()

        val caballo =
            etCaballo.text.toString().trim()

        val comentario =
            etComentario.text.toString().trim()

        if (jinete.isEmpty() ||
            movil.isEmpty() ||
            caballo.isEmpty() ||
            fechaSeleccionada.isEmpty() ||
            horaSeleccionada.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Completa todos los campos obligatorios",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (movil.length < 9) {

            Toast.makeText(
                this,
                "Introduce un móvil válido",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val reserva = Reserva(

            id = reservaEnEdicion?.id ?: 0,

            nombreJinete = jinete,
            movil = movil,
            nombreCaballo = caballo,
            fecha = fechaSeleccionada,
            hora = horaSeleccionada,
            comentario = comentario
        )

        if (reservaEnEdicion == null) {

            viewModel.insertar(reserva)

            Toast.makeText(
                this,
                "Reserva guardada correctamente",
                Toast.LENGTH_SHORT
            ).show()

            enviarWhatsApp(
                movil,
                caballo,
                fechaSeleccionada,
                horaSeleccionada
            )

        } else {

            viewModel.actualizar(reserva)

            Toast.makeText(
                this,
                "Reserva actualizada correctamente",
                Toast.LENGTH_SHORT
            ).show()

            reservaEnEdicion = null

            btnGuardar.text = "Guardar reserva"
        }

        limpiarFormulario()
    }

    private fun buscarReservas() {

        if (fechaSeleccionada.isEmpty() ||
            horaSeleccionada.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Selecciona fecha y hora",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        viewModel.buscarPorFechaYHora(
            fechaSeleccionada,
            horaSeleccionada
        ) { reservas ->

            adapter.actualizarLista(reservas)

            if (reservas.isEmpty()) {

                Toast.makeText(
                    this,
                    "No hay reservas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun limpiarFormulario() {

        etJinete.text.clear()
        etMovil.text.clear()
        etCaballo.text.clear()
        etComentario.text.clear()

        fechaSeleccionada = ""
        horaSeleccionada = ""

        btnFecha.text = "Elegir fecha"
        btnHora.text = "Elegir hora"

        reservaEnEdicion = null
    }

    private fun convertirFechaParaMostrar(
        fechaBD: String
    ): String {

        return try {

            val fecha = formatoBD.parse(fechaBD)

            if (fecha != null) {
                formatoMostrar.format(fecha)
            } else {
                fechaBD
            }

        } catch (e: Exception) {

            fechaBD
        }
    }

    private fun enviarWhatsApp(
        movil: String,
        caballo: String,
        fecha: String,
        hora: String
    ) {

        val fechaMostrar =
            convertirFechaParaMostrar(fecha)

        val mensaje =
            "Hola, tu reserva para el paseo con el caballo $caballo el día $fechaMostrar a las $hora está confirmada."

        val url =
            "https://api.whatsapp.com/send?phone=$movil&text=${Uri.encode(mensaje)}"

        val intent = Intent(Intent.ACTION_VIEW)

        intent.data = Uri.parse(url)

        try {

            startActivity(intent)

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "WhatsApp no está instalado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}