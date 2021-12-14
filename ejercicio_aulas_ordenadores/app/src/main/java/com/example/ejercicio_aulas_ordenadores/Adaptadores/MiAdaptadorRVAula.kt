package com.example.ejercicio_aulas_ordenadores.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_aulas_ordenadores.Modelo.Profesor
import com.example.ejercicio_aulas_ordenadores.Modelo.Aula
import com.example.ejercicio_aulas_ordenadores.Modelo.Ordenador
import com.example.ejercicio_aulas_ordenadores.R

class MiAdaptadorRVAula (private var context: Context,
                             private var aulas : ArrayList<Aula>
) :
    RecyclerView.Adapter<MiAdaptadorRVAula.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.aulas_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id_aula.text = aulas[position].id_aula
        holder.nombre_aula.text = aulas[position].nombre_aula

        holder.itemView.setOnClickListener {
            Toast.makeText(context, aulas[position].id_aula, Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return aulas.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id_aula: TextView = itemView.findViewById<View>(R.id.txtIdAula) as TextView
        var nombre_aula: TextView = itemView.findViewById<View>(R.id.txtNombreAula) as TextView

    }
}