package com.idat.proyectoevc4.vista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idat.proyectoevc4.adaptador.Adaptador
import com.idat.proyectoevc4.databinding.FragmentFragActivitiListarCBinding
import com.idat.proyectoevc4.modelo.Cita
import com.idat.proyectoevc4.servicio.RetrofitCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class fragActivitiListarC : Fragment() {

    // View Binding para este fragmento
    private var _binding: FragmentFragActivitiListarCBinding? = null
    private val binding get() = _binding!!

    private var listaCitas = ArrayList<Cita>()
    private var adaptador: Adaptador = Adaptador()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFragActivitiListarCBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        asignarReferencias()
        cargarCitas()
    }

    private fun asignarReferencias() {
        binding.rvCitas.layoutManager = LinearLayoutManager(requireContext())
        binding.btnNuevaCita.setOnClickListener {
            val intent = Intent(requireContext(), IngresarCitaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarCitas() {
        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitCliente.objWebService.listarCita()
            requireActivity().runOnUiThread {
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
        binding.rvCitas.adapter = adaptador
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
