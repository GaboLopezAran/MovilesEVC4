package com.idat.proyectoevc4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idat.proyectoevc4.modelo.LoginRequest
import com.idat.proyectoevc4.servicio.RetrofitCliente
import com.idat.proyectoevc4.vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_loguin : AppCompatActivity() {

    private lateinit var etCorreoL : EditText
    private lateinit var etContrasenaL : EditText
    private lateinit var btnLogin : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loguin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()

    }

    private fun asignarReferencias() {
        etCorreoL = findViewById(R.id.etCorreoL)
        etContrasenaL = findViewById(R.id.etContrasenaL)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener{
            realizarLogin()
        }
    }

    private fun realizarLogin() {
        val correo = etCorreoL.text.toString()
        val contrasena = etContrasenaL.text.toString()

        if (correo.isEmpty() && contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val loginRequest = LoginRequest(correo, contrasena)

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitCliente.objWebService.login(loginRequest)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val message = response.body()
                    Toast.makeText(this@activity_loguin, "Inicio de Sesión Exitoso", Toast.LENGTH_SHORT).show()
                    //rederigir a la vista principal
                    val intent = Intent(this@activity_loguin, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@activity_loguin, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}