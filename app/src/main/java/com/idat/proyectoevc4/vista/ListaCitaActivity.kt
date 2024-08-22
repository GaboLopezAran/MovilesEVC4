package com.idat.proyectoevc4.vista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.adaptador.Adaptador
import com.idat.proyectoevc4.modelo.Cita
import com.idat.proyectoevc4.servicio.RetrofitCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaCitaActivity : AppCompatActivity() {

    private lateinit var rvCitas: RecyclerView

    private var listaCitas=ArrayList<Cita>()

    private var adaptador: Adaptador =Adaptador()

    private lateinit var btnNuevaCita:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_cita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        asignarReferencias()
        cargarCitas()
    }

    private  fun asignarReferencias() {
        btnNuevaCita=findViewById(R.id.btnNuevaCita)
        rvCitas=findViewById(R.id.rvCitas)
        rvCitas.layoutManager=LinearLayoutManager(this)
        btnNuevaCita.setOnClickListener{
            val intent = Intent(this, IngresarCitaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarCitas() {
        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitCliente.objWebService.listarCita()
            runOnUiThread {
                if (rpta.isSuccessful) {
                    listaCitas = (rpta.body() ?: ArrayList()) as ArrayList<Cita>
                    mostrarCitas()
                } else {
                    Log.d("===", "ERROR EN LA PETICIÓN")
                }
            }
        }
    }

    private fun mostrarCitas() {
        adaptador.agregarCitas(listaCitas)
        rvCitas.adapter=adaptador
    }

}