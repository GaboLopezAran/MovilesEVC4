package com.idat.proyectoevc4.vista

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.modelo.Dueno
import androidx.appcompat.app.AlertDialog
import com.idat.proyectoevc4.servicio.RetrofitCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Activity_registrar_dueno : AppCompatActivity() {

    private lateinit var etnombre: EditText
    private lateinit var etapellido : EditText
    private lateinit var etDni : EditText
    private lateinit var etDireccion : EditText
    private lateinit var etTelefono : EditText
    private lateinit var etCorreo : EditText
    private lateinit var etContracena : EditText
    private lateinit var btnRegistrarDueno : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_dueno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    private fun asignarReferencias() {

        //Se asignan las variables para trabajar
        etnombre = findViewById(R.id.etnombre)
        etapellido = findViewById(R.id.etapellido)
        etDni   = findViewById(R.id.etDni)
        etDireccion = findViewById(R.id.etDireccion)
        etTelefono = findViewById(R.id.etTelefono)
        etCorreo = findViewById(R.id.etCorreo)
        etContracena = findViewById(R.id.etContracena)
        btnRegistrarDueno = findViewById(R.id.btnRegistrarDueno)

        btnRegistrarDueno.setOnClickListener{
            //Se extrae el contenido
            var nombre = etnombre.text.toString()
            var apellido = etapellido.text.toString()
            var dni = etDni.text.toString().toInt()
            var direccion = etDireccion.text.toString()
            var telefono = etTelefono.text.toString().toInt()
            var correo = etCorreo.text.toString()
            var contrasena = etContracena.text.toString()

            var dueno = Dueno(0,nombre,apellido,dni,direccion,correo,telefono,contrasena)

            CoroutineScope(Dispatchers.IO).launch {
                var rpta = RetrofitCliente.objWebService.ingresarDueno(dueno)
                runOnUiThread {
                    if (rpta.isSuccessful) {
                        mostrarMensaje("El dueño fue registrado satisfactoriamente")
                    }
                }
            }
        }
    }

    private fun mostrarMensaje(mensaje:String){
        var ventana = AlertDialog.Builder(this)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }
}