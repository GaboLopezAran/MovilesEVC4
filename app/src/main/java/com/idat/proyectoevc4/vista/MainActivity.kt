package com.idat.proyectoevc4.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.activity_loguin

class MainActivity : AppCompatActivity() {

    public lateinit var btnRegistroDueno: Button
    public lateinit var btnLoginPrueba: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    private fun asignarReferencias() {
        btnRegistroDueno = findViewById(R.id.btnRegistroDueno)
        btnLoginPrueba = findViewById(R.id.btnLoginPrueba)

        btnLoginPrueba.setOnClickListener {
            val intent = Intent(this, activity_loguin::class.java) // Cambia el nombre de la clase a mayúscula
            startActivity(intent) // Inicia la nueva actividad
        }

        btnRegistroDueno.setOnClickListener {
            val intent = Intent(this, Activity_registrar_dueno::class.java) // Cambia el nombre de la clase a mayúscula
            startActivity(intent) // Inicia la nueva actividad
        }
    }
}