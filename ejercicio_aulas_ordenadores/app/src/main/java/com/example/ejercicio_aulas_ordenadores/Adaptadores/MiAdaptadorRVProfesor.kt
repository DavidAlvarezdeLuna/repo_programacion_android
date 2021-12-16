package com.example.ejercicio_aulas_ordenadores.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import com.example.ejercicio_aulas_ordenadores.R

class MiAdaptadorRVProfesor (private var context: Context,
                             private var profesores : ArrayList<Profesor>
) :
    RecyclerView.Adapter<MiAdaptadorRVProfesor.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profesores_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = profesores[position].id
        holder.nombre.text = profesores[position].nombre

        if(profesores[position].permisos == "0"){
            holder.permisos.text = "Usuario"
        }else{
            holder.permisos.text = "Jefe"
        }


        holder.itemView.setOnClickListener {
            Toast.makeText(context, profesores[position].id, Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return profesores.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById<View>(R.id.txtId) as TextView
        var nombre: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var permisos: TextView = itemView.findViewById<View>(R.id.txtPermisos) as TextView
    }
}