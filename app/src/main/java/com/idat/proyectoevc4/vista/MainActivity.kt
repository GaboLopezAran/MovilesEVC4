package com.idat.proyectoevc4.vista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarFragmento(fragActivitiListarC())
        mostrarFrangmento()

    }
    private fun mostrarFrangmento(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_listac->cargarFragmento(fragActivitiListarC())
            }
            true
        }
    }
    private fun cargarFragmento(fragment: Fragment){
        val transaccion=supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.frameLayout,fragment)
        transaccion.commit()

    }

}