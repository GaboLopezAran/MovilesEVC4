package com.idat.proyectoevc4.vista

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.modelo.Cita
import com.idat.proyectoevc4.servicio.RetrofitCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class MascotaItem(val id: Int, val nombre: String)

class IngresarCitaActivity : AppCompatActivity() {

    private lateinit var spMacotas: Spinner
    private lateinit var edtFehaC: EditText
    private lateinit var edtMotivoC: EditText
    private lateinit var btnRegistrarCita: Button

    private var mascotaItems: List<MascotaItem> = listOf() // Lista para almacenar las mascotas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingresar_cita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    private fun asignarReferencias() {

        // Se asignan las variables para trabajar
        spMacotas = findViewById(R.id.spMacotas)
        edtFehaC = findViewById(R.id.edtFechaC)
        edtMotivoC = findViewById(R.id.edtMotivoC)
        btnRegistrarCita = findViewById(R.id.btnRegistrarCita)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitCliente.objWebService.listarMascota()
                if (response.isSuccessful) {
                    val mascotas = response.body()
                    mascotas?.let {
                        mascotaItems = it.map { mascota ->
                            MascotaItem(mascota.id_mascota, mascota.nombre)
                        }

                        // Actualiza el UI en el hilo principal
                        runOnUiThread {
                            val adapter = ArrayAdapter(
                                this@IngresarCitaActivity,
                                android.R.layout.simple_spinner_item,
                                mascotaItems.map { item -> item.nombre }
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            spMacotas.adapter = adapter
                        }
                    }
                } else {
                    Log.e("IngresarCitaActivity", "Error en la respuesta: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("IngresarCitaActivity", "Error al obtener las mascotas", e)
            }
        }

        btnRegistrarCita.setOnClickListener {
            // Se extrae el contenido
            val fecha = edtFehaC.text.toString()
            val motivo = edtMotivoC.text.toString()

            val posicionSeleccionada = spMacotas.selectedItemPosition
            if (posicionSeleccionada != -1) {
                // Obtener el ID de la mascota seleccionada
                val idMascotaSeleccionada = mascotaItems[posicionSeleccionada].id

                // Crear un objeto Cita con el ID de la mascota seleccionada
                val cita = Cita(0,idMascotaSeleccionada,fecha,motivo )

                // Registrar la cita
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitCliente.objWebService.ingresarCita(cita)
                        if (response.isSuccessful) {
                            runOnUiThread {
                                mostrarMensaje("Cita registrada con éxito")
                            }
                        } else {
                            Log.e("IngresarCitaActivity", "Error en la respuesta: ${response.message()}")
                        }
                    } catch (e: Exception) {
                        Log.e("IngresarCitaActivity", "Error al registrar la cita", e)
                    }
                }
            } else {
                runOnUiThread {
                    mostrarMensaje("Por favor, selecciona una mascota")
                }
            }
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(this, ListaCitaActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }
}
