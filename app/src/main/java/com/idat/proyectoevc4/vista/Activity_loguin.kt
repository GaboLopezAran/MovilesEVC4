package com.idat.proyectoevc4.vista

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.modelo.LoginRequest
import com.idat.proyectoevc4.servicio.RetrofitCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException

class Activity_loguin : AppCompatActivity() {

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

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val loginRequest = LoginRequest(correo, contrasena)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitCliente.objWebService.login(loginRequest)
                val responseBody = response.body()?.string()

                runOnUiThread {
                    if (response.isSuccessful && responseBody?.contains("exitoso") == true) {
                        Toast.makeText(this@Activity_loguin, "Inicio de Sesión Exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Activity_loguin, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Activity_loguin, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginError", "Error en la solicitud de inicio de sesión", e)
                runOnUiThread {
                    Toast.makeText(this@Activity_loguin, "Ocurrió un error al intentar iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun onclikRegistrate(view: View) {
        val intent = Intent(this, Activity_registrar_dueno::class.java)
        // Iniciar la actividad
        startActivity(intent)
    }


}