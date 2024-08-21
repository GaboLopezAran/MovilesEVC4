package com.idat.proyectoevc4.vista

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

class IngresarCitaActivity : AppCompatActivity() {

    private lateinit var edtMascotaC:EditText
    private lateinit var edtFehaC:EditText
    private lateinit var edtMotivoC:EditText
    private lateinit var btnRegistrarCita: Button

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

        //Se asignan las variables para trabajar
        edtMascotaC=findViewById(R.id.edtMascotaC)
        edtFehaC=findViewById(R.id.edtFechaC)
        edtMotivoC=findViewById(R.id.edtMotivoC)
        btnRegistrarCita=findViewById(R.id.btnRegistrarCita)

        btnRegistrarCita.setOnClickListener {
            // Se extrae el contenido
            val mascota = edtMascotaC.text.toString().toIntOrNull() ?: 0
            val fecha = edtFehaC.text.toString()
            val motivo = edtMotivoC.text.toString()

            val cita = Cita(0, mascota, motivo, fecha)

            CoroutineScope(Dispatchers.IO).launch {
                val rpta = RetrofitCliente.objWebService.ingresarCita(cita)
                runOnUiThread {
                   if(rpta.isSuccessful){
                       mostrarMensaje("La cita fue separada satisfactoriamente")
                   }
                }
            }

        }
    }
    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }
}