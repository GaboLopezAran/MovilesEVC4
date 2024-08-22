package com.idat.proyectoevc4.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idat.proyectoevc4.R
import com.idat.proyectoevc4.modelo.Cita

class Adaptador:RecyclerView.Adapter<Adaptador.MiViewHolder>() {

    private var listaCitas=ArrayList<Cita>()

    fun agregarCitas(citas:ArrayList<Cita>){
        listaCitas=citas
    }


    class MiViewHolder (vista:View): RecyclerView.ViewHolder(vista){
        private var txtMascotafila=vista.findViewById<TextView>(R.id.txtMascotafila)
        private var txtMotivolista=vista.findViewById<TextView>(R.id.txtMotivolista)
        private var txtFechalista=vista.findViewById<TextView>(R.id.txtFechalista)

        fun rellenarFila(cita: Cita){
            txtMascotafila.text=cita.id_mascota.toString()
            txtMotivolista.text=cita.motivo
            txtFechalista.text=cita.fecha
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adaptador.MiViewHolder {
       val vista=LayoutInflater.from(parent.context).inflate(R.layout.fila_cita,parent,false)
        return MiViewHolder(vista)
    }

    override fun onBindViewHolder(holder: Adaptador.MiViewHolder, position: Int) {
        val citaItem=listaCitas[position]
        holder.rellenarFila(citaItem)

    }

    override fun getItemCount(): Int {
        return listaCitas.size
    }

}